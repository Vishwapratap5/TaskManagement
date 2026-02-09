package com.taskmanagement.taskmanagement.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="work_Flows")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkFlow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workflowId;

    @Column(unique = true,nullable = false)
    private String workflowName;

    @Column(length =1000,nullable = false)
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "workflow")
    private List<WorkFlowTransaction> transactions=new ArrayList<>();
}
