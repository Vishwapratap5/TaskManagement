package com.taskmanagement.taskmanagement.Security;

import com.taskmanagement.taskmanagement.Entity.Issue;
import com.taskmanagement.taskmanagement.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return issueRepo.save(issue);
    }
}
