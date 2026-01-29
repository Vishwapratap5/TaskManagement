package com.taskmanagement.taskmanagement.Repository;

import com.taskmanagement.taskmanagement.Entity.Subtask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubtaskRepo extends JpaRepository<Subtask,Long> {
}
