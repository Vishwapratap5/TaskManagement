package com.taskmanagement.taskmanagement.Repository;

import com.taskmanagement.taskmanagement.Entity.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintRepo extends JpaRepository<Sprint,Long> {
}
