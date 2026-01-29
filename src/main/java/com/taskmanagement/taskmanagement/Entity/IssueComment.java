package com.taskmanagement.taskmanagement.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="issue_comments")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class IssueComment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Long issueId;

    private String authorEmail;

    @Column(columnDefinition = "TEXT")
    private String body;

    private LocalDateTime createdAt=LocalDateTime.now();
}
