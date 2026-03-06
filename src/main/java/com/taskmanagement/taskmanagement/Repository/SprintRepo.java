package com.taskmanagement.taskmanagement.Repository;

import com.taskmanagement.taskmanagement.Entity.Sprint;
import com.taskmanagement.taskmanagement.Enums.SprintStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SprintRepo extends JpaRepository<Sprint,Long> {

    List<Sprint> findByProjectId(Long projectId);
    List<Sprint> findBySprintStatus(SprintStatus sprintStatus);

}
