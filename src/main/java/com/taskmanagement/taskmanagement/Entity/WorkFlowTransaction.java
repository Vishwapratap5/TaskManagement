package com.taskmanagement.taskmanagement.Entity;


import com.taskmanagement.taskmanagement.Enums.IssueStatus;
import com.taskmanagement.taskmanagement.Enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="work_folw_transactions",indexes= {@Index(name="idx_wf_from_to",columnList="workflow_id,fromStatus,toStatus")})
public class WorkFlowTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="workflow_id",nullable=false)
    private WorkFlow workFlow;

    @Column(nullable=false)
    private IssueStatus fromStatus;

    @Column(nullable=false)
    private IssueStatus toStatus;

    private String name;

    private Set<Role>allowedRole= new HashSet<>();


}

