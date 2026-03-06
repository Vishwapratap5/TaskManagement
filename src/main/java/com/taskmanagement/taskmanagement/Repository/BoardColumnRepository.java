package com.taskmanagement.taskmanagement.Repository;

import com.taskmanagement.taskmanagement.Entity.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardColumnRepository extends JpaRepository<BoardColumn, Long> {
    List<BoardColumn> findByBoard_BoardIdOrderByPosition(Long boardId);
}