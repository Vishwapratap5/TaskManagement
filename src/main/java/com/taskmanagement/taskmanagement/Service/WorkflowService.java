package com.taskmanagement.taskmanagement.Service;

import com.taskmanagement.taskmanagement.Entity.WorkFlow;
import com.taskmanagement.taskmanagement.Entity.WorkFlowTransaction;
import com.taskmanagement.taskmanagement.Enums.IssueStatus;
import com.taskmanagement.taskmanagement.Enums.Role;
import com.taskmanagement.taskmanagement.Repository.WorkFlowRepo;
import com.taskmanagement.taskmanagement.Repository.WorkFlowTransactionRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowService {

    @Autowired
    private WorkFlowRepo workFlowRepo;
    @Autowired
    private WorkFlowTransactionRepo workFlowTransactionRepo;

    @Autowired
    private WorkFlowTransactionRepo transactionRepo;

    @Transactional
    public WorkFlow createWorkFlow(WorkFlow workFlow) {
        if(workFlow.getTransactions()!=null) {
            for(WorkFlowTransaction workFlowTransaction : workFlow.getTransactions()) {
                workFlowTransaction.setWorkflowId(workFlow);
            }
        }
        return workFlowRepo.save(workFlow);
    }

    public List<WorkFlow> getAllWorkFlow() {
        return workFlowRepo.findAll();
    }

    public WorkFlow getWorkFlowById(Long workFlowId) {
        return workFlowRepo.findById(workFlowId).orElseThrow(()->new RuntimeException("Work Flow Not Found"));
    }

    @Transactional
    public WorkFlow updateWorkFlow(Long id,WorkFlow workFlow) {
        WorkFlow storedWorkFlow = getWorkFlowById(id);

        storedWorkFlow.setWorkflowName(workFlow.getWorkflowName());
        storedWorkFlow.setDescription(workFlow.getDescription());
        storedWorkFlow.setWorkflowId(workFlow.getWorkflowId());
        storedWorkFlow.getTransactions().clear();
        if(workFlow.getTransactions()!=null) {
            for(WorkFlowTransaction workFlowTransaction : workFlow.getTransactions()) {
                workFlowTransaction.setWorkflowId(workFlow);
                storedWorkFlow.getTransactions().add(workFlowTransaction);
            }
        }
        return workFlowRepo.save(storedWorkFlow);

    }

    private WorkFlow getById(Long id) {
        return workFlowRepo.findById(id).orElseThrow(()->new RuntimeException("Work Flow Not Found"));
    }

    public void deleteWorkFlow(Long id) {
        workFlowRepo.deleteById(id);
    }

    public List<WorkFlowTransaction> allowedTransactions(WorkFlow workflow, IssueStatus status) {

        if(!workFlowRepo.findById(workflow.getWorkflowId()).isPresent()) {
          throw new RuntimeException("Work Flow Not Found");
        }
        return transactionRepo.findByWorkflowIdAndFromStatus(workflow,status);

    }

//    public boolean isTransactionAllowed(Long workflowId, IssueStatus fromStatus, IssueStatus toStatus, Set<Role> userRoles) {
//
//    }
}
