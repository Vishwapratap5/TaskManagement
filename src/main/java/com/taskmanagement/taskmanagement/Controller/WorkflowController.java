package com.taskmanagement.taskmanagement.Controller;

import com.taskmanagement.taskmanagement.Entity.WorkFlow;
import com.taskmanagement.taskmanagement.Entity.WorkFlowTransaction;
import com.taskmanagement.taskmanagement.Enums.IssueStatus;
import com.taskmanagement.taskmanagement.Enums.Role;
import com.taskmanagement.taskmanagement.Service.WorkFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/workflows")

@RequiredArgsConstructor
public class WorkflowController {

    @Autowired
    private WorkFlowService workFlowService;

    @PostMapping("/create")
    public ResponseEntity<WorkFlow> create(@RequestBody WorkFlow wf) {
        return ResponseEntity.ok(workFlowService.createWorkFlow(wf));
    }

    @GetMapping("/all")
    public ResponseEntity<List<WorkFlow>> getAll() {
        return ResponseEntity.ok(workFlowService.getAllWorkFlowList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkFlow> getById(@PathVariable Long id) {
        return ResponseEntity.ok(workFlowService.workFlowGetById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<WorkFlow> update(@PathVariable Long id, @RequestBody WorkFlow wf) {
        return ResponseEntity.ok(workFlowService.updateWorkFlow(id, wf));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        workFlowService.deleteWorkFlow(id);
        return ResponseEntity.ok("Deleted");
    }

    @GetMapping("/{id}/transaction")
    public ResponseEntity<List<WorkFlowTransaction>> allowed(@PathVariable Long id, @RequestParam IssueStatus fromStatus) {
        return ResponseEntity.ok(workFlowService.allowedTransactions(id, fromStatus));
    }

    @PostMapping("/{id}/valid-transaction")
    public ResponseEntity<Boolean> validTransactions(@PathVariable("id") Long workFlowId,
                                                     @RequestParam IssueStatus fromStatus,
                                                     @RequestParam IssueStatus toStatus,
                                                     @RequestBody Set<Role> userRoles) {
        boolean allowed = workFlowService.isTransactionsAllowed(workFlowId, fromStatus, toStatus, userRoles);

        return ResponseEntity.ok(allowed);
    }

    @GetMapping("/name")
    public ResponseEntity<Optional<WorkFlow>> getName(@RequestParam("name") String workFlowName) {
        return ResponseEntity.ok(workFlowService.findByName(workFlowName));
    }

}