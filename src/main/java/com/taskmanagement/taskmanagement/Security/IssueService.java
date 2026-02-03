package com.taskmanagement.taskmanagement.Security;

import com.taskmanagement.taskmanagement.Entity.*;
import com.taskmanagement.taskmanagement.Enums.IssuePriority;
import com.taskmanagement.taskmanagement.Enums.IssueStatus;
import com.taskmanagement.taskmanagement.Enums.IssueType;
import com.taskmanagement.taskmanagement.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class IssueService {

    @Autowired
    private IssueCommentRepo issueCommentRepo;

    @Autowired
    private IssueRepo issueRepo;

    @Autowired
    private EpicRepo epicRepo;

    @Autowired
    private SprintRepo sprintRepo;

    @Autowired
    private SubtaskRepo subtaskRepo;



    private String generateIssueKey(Long id){
        return "PROJ - "+id;
    }

    @Transactional
    public Issue createIssue(Issue issue){
        if(issue.getIssueType()==null){
            issue.setIssueType(IssueType.TASK);
        }

        if(issue.getIssuePriority()==null){
            issue.setIssuePriority(IssuePriority.MEDIUM);
        }

        issue.setIssueStatus(IssueStatus.OPEN);

        issue.setIssueKey((generateIssueKey(issue.getId())));


        return issueRepo.save(issue);
    }



    public Issue getIssueById(Long id){
        return issueRepo.findById(id).orElseThrow(()->new RuntimeException("Issue not found"));
    }

    public List<Issue> getIssueByAssigneeEmailId(String assigneeEmailId){
        return issueRepo.findByAssigneeEmailId(assigneeEmailId);
    }

    public List<Issue> getIssueBySprintId(Long sprintId){
        return issueRepo.findBySprintId(sprintId);
    }

    public List<Issue> getIssueByEpicId(Long epicId){
        return issueRepo.findByEpicId(epicId);
    }

    @Transactional
    public Issue updateStatus(Long id, IssueStatus issueStatus,String performedBy){
        Issue issue = issueRepo.findById(id).orElseThrow(()->new RuntimeException("Issue not Found..!"));

        try{
            IssueStatus newStatus=IssueStatus.valueOf(issueStatus.toString());
            issue.setIssueStatus(newStatus);
        }catch(Exception e){
            throw new  RuntimeException("Invalid IssueStatus");
        }
        issue.setUpdatedDate(LocalDateTime.now());
        return issueRepo.save(issue);
    }

    @Transactional
    public IssueComment addComment(Long issueId,String authorEmail,String body){
        Issue issue = issueRepo.findById(issueId).orElseThrow(()->new RuntimeException("Issue not Found..!"));

        IssueComment comment=new IssueComment();
        comment.setIssueId(issueId);
        comment.setAuthorEmail(authorEmail);
        comment.setBody(body);

        return issueCommentRepo.save(comment);

    }

    @Transactional
    public List<Issue> search(Map<String,String> filter){

        if(filter.containsKey("assigneeEmailId")){
            return issueRepo.findByAssigneeEmailId(filter.get("assigneeEmailId"));
        }

        if(filter.containsKey("sprintId")){
            Long sprintId=Long.valueOf(filter.get("sprintId"));
            return getIssueBySprintId(sprintId);
        }

        if(filter.containsKey("IssueStatus")){
            IssueStatus issueStatus=IssueStatus.valueOf(filter.get("IssueStatus").toUpperCase());
            return issueRepo.findByIssueStatus(issueStatus);
        }

        return issueRepo.findAll();
    }

    @Transactional
    public Sprint createSprint(Sprint sprint){
        return sprintRepo.save(sprint);
    }

    @Transactional
    public Epic createEpic(Epic epic){
        return epicRepo.save(epic);
    }

    @Transactional
    public Subtask createSubtask(Subtask subtask){
        return subtaskRepo.save(subtask);
    }
}
