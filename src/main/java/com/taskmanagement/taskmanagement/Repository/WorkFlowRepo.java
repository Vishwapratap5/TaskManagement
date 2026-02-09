package com.taskmanagement.taskmanagement.Repository;

import com.taskmanagement.taskmanagement.Entity.WorkFlow;
import com.taskmanagement.taskmanagement.Entity.WorkFlowTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkFlowRepo extends JpaRepository<WorkFlow, Long> {

  Optional<WorkFlow> findByWorkflowName(String workflowName);

}