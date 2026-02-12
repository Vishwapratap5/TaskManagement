package com.taskmanagement.taskmanagement.Repository;

import com.taskmanagement.taskmanagement.Entity.WorkFlow;
import com.taskmanagement.taskmanagement.Entity.WorkFlowTransaction;
import com.taskmanagement.taskmanagement.Enums.IssueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkFlowTransactionRepo extends JpaRepository<WorkFlowTransaction, Long> {
  List<WorkFlowTransaction> findByWorkflowId(WorkFlow workflowId);
  List<WorkFlowTransaction> findByWorkflowIdAndFromStatus(WorkFlow workflowId, IssueStatus fromStatus);
}