package com.taskmanagement.taskmanagement.Entity;

import com.taskmanagement.taskmanagement.Enums.IssuePriority;
import com.taskmanagement.taskmanagement.Enums.IssueStatus;
import com.taskmanagement.taskmanagement.Enums.IssueType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "issues")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String issueTitle;

    @Column(length = 2000)
    private String issueDescription;

    private String issueKey;

    @Enumerated(EnumType.STRING)
    private IssueType issueType;

    @Enumerated(EnumType.STRING)
    private IssueStatus issueStatus;

    @Enumerated(EnumType.STRING)
    private IssuePriority issuePriority;

    private String assigneeEmailId;

    private String reporterEmailId;

    private Long epicId;
    private Long SprintId;


    private LocalDateTime issueDate=LocalDateTime.now();

    private LocalDateTime updatedDate=LocalDateTime.now();

    private LocalDateTime dueDate;

}
