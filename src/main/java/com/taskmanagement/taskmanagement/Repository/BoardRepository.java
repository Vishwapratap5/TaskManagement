package com.taskmanagement.taskmanagement.Repository;

import com.taskmanagement.taskmanagement.Entity.Board;
import com.taskmanagement.taskmanagement.Entity.BoardCard;
import com.taskmanagement.taskmanagement.Entity.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<BoardColumn> findByBoardIdOrderByPosition(Long boardId);

    List<BoardCard> findByIdAndColumnOrderByPostion(Long boardId, Long columnId);
}