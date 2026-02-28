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
import java.util.Optional;
import java.util.Set;


@Service
@AllArgsConstructor
@NoArgsConstructor
public class WorkFlowService {

    @Autowired
    private WorkFlowRepo workFlowRepo;


    @Autowired
    private WorkFlowTransactionRepo transactionRepo;


    @Transactional
    public WorkFlow createWorkFlow(WorkFlow wf) {

        if(wf.getTransactions()!=null) {
            for(WorkFlowTransaction t: wf.getTransactions()) {
                t.setWorkFlow(wf);
            }
        }

        return workFlowRepo.save(wf);
    }

    public List<WorkFlow>getAllWorkFlowList(){
        return workFlowRepo.findAll();
    }

    public WorkFlow workFlowGetById(Long id) {
        return workFlowRepo.findById(id).orElseThrow(()-> new RuntimeException("WorkFlow not found"));
    }


    @Transactional
    public WorkFlow updateWorkFlow(Long id , WorkFlow update) {

        WorkFlow wf= getById(id);

        wf.setWorkFlowName(update.getWorkFlowName());
        wf.setDescription(update.getDescription());
        wf.getTransactions().clear();

        if(update.getTransactions()!=null) {
            for(WorkFlowTransaction t:update.getTransactions()) {
                t.setWorkFlow(wf);
                wf.getTransactions().add(t);
            }
        }
        return workFlowRepo.save(wf);
    }

    private WorkFlow getById(Long id) {

        return workFlowRepo.findById(id).orElseThrow(()-> new RuntimeException("WorkFlow not found"));
    }

    @Transactional
    public void deleteWorkFlow(Long id) {
        workFlowRepo.deleteById(id);
    }



    public List<WorkFlowTransaction>allowedTransactions(Long workFlowId,IssueStatus fromStatus){
        return transactionRepo.findByWorkFlowIdAndFromStatus(workFlowId, fromStatus);
    }


    public boolean isTransactionsAllowed(Long workFlowId,
                                         IssueStatus fromStatus,
                                         IssueStatus toStatus,
                                         Set<Role>userRoles) {

        List<WorkFlowTransaction>transaction= transactionRepo.findByWorkFlowIdAndFromStatus(workFlowId, fromStatus);

        for(WorkFlowTransaction t:transaction) {

            if(!t.getToStatus().equals(toStatus)) {
                continue;
            }

//			Role allowedRole= t.getAllowedRole();

            if(t.getAllowedRole() == null || t.getAllowedRole().isEmpty()) {
                return true;
            }

            for(Role role:userRoles) {
                if(t.getAllowedRole().contains(role)) {
                    return true;
                }
            }

            return false;
        }

        return false;
    }
    public Optional<WorkFlow>findByName(String workFlowName){
        return workFlowRepo.findByWorkFlowName(workFlowName);
    }

}

