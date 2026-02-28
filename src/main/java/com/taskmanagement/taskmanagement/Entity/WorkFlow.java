package com.taskmanagement.taskmanagement.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="work_flows")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkFlow {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true,nullable=false)
    private String workFlowName;

    @Column(length=1000)
    private String description;

    private LocalDateTime createdAt= LocalDateTime.now();

    @OneToMany(mappedBy="workFlow")
    private List<WorkFlowTransaction> transactions= new ArrayList<>();


}


