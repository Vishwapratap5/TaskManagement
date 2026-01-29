package com.taskmanagement.taskmanagement.Repository;

import com.taskmanagement.taskmanagement.Entity.Epic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EpicRepo extends JpaRepository<Epic,Long> {
}
