package com.taskmanagement.taskmanagement.Entity;

import com.taskmanagement.taskmanagement.Enums.IssueStatus;
import com.taskmanagement.taskmanagement.Enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="workFlow_Transactions" ,indexes = {@Index(name = "idx_wf_from_to",columnList = "workflow_id,formStatus,toStatus")})
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WorkFlowTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long TransactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="workflow_id",nullable=false)
    private WorkFlow workflowId;

    @Column(nullable=false)
    private IssueStatus fromStatus;

    @Column(nullable=false)
    private IssueStatus toStatus;


    private String name;

    private Role allowedRole;

}
