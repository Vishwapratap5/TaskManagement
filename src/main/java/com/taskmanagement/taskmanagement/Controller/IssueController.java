package com.taskmanagement.taskmanagement.Controller;


import com.taskmanagement.taskmanagement.Entity.Issue;
import com.taskmanagement.taskmanagement.Entity.IssueComment;
import com.taskmanagement.taskmanagement.Enums.IssueStatus;
import com.taskmanagement.taskmanagement.Service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/issues/")
@RequiredArgsConstructor
public class IssueController {

    @Autowired
    private IssueService issueService;

    @PostMapping("/createIssue")
    public ResponseEntity<String> createIssue(@RequestBody Issue issue){
//        System.out.println("Create Issue API called");
//        return ResponseEntity.ok(issueService.createIssue(issue));

        System.out.println(">>> CREATE ISSUE API HIT <<<");

        return ResponseEntity.ok("Controller reached");
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<Issue> updateIssueStatus(@PathVariable("id") Long id,
                                                   @RequestParam IssueStatus issueStatus){
        return ResponseEntity.ok(issueService.updateStatus(id, issueStatus));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<IssueComment> addComment(@PathVariable("id") Long IssueId, @RequestParam String authorEmail, @RequestBody Map<String, String> body){
        String commentBody=body.get("commentBody");
        String author=(authorEmail==null)?body.getOrDefault("authorEmail","system@gmail"):authorEmail;
        return ResponseEntity.ok(issueService.addComment(IssueId, author, commentBody));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Issue>> searchIssue(@RequestParam Map<String, String> filter){
        return  ResponseEntity.ok(issueService.search(filter));
    }

}
