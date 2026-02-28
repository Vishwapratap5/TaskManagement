package com.taskmanagement.taskmanagement.Repository;

import com.taskmanagement.taskmanagement.Entity.BoardCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardCardRepository extends JpaRepository<BoardCard, Integer> {
    Optional<BoardCard> findByIssueId(Long issueId);

    long countByBoardIdAndColumnId(Long boardId, Long columnId);

    List<BoardCard> findByBoardIdAndColumnOrderByPosition(Long boardId, Long columnId);
}