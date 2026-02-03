package com.taskmanagement.taskmanagement.Repository;

import com.taskmanagement.taskmanagement.Entity.Issue;
import com.taskmanagement.taskmanagement.Enums.IssueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepo extends JpaRepository<Issue,Long> {
    List<Issue> findByAssigneeEmailId(String assigneeEmail);
    List<Issue> findBySprintId(Long sprintId);
    List<Issue> findByIssueStatus(IssueStatus issueStatus);
    List<Issue> findByIssueKey(String issueKey);
    List<Issue> findByEpicId(Long epicId);
    Issue FindById(Long id);
}
