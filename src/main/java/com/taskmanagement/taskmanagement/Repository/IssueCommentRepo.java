package com.taskmanagement.taskmanagement.Repository;

import com.taskmanagement.taskmanagement.Entity.IssueComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueCommentRepo extends JpaRepository<IssueComment,Long> {

    List<IssueComment> findByIssueIdOrderByCreatedAt(Long issueId);
}
