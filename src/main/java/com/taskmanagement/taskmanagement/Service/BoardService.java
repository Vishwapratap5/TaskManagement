package com.taskmanagement.taskmanagement.Service;

import com.taskmanagement.taskmanagement.Entity.Board;
import com.taskmanagement.taskmanagement.Entity.BoardCard;
import com.taskmanagement.taskmanagement.Entity.BoardColumn;
import com.taskmanagement.taskmanagement.Entity.Issue;
import com.taskmanagement.taskmanagement.Enums.IssueStatus;
import com.taskmanagement.taskmanagement.Repository.BoardCardRepository;
import com.taskmanagement.taskmanagement.Repository.BoardColumnRepository;
import com.taskmanagement.taskmanagement.Repository.BoardRepository;
import com.taskmanagement.taskmanagement.Repository.IssueRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardColumnRepository boardColumnRepository;

    @Autowired
    private BoardCardRepository boardCardRepository;

    @Autowired
    private IssueRepo issueRepo;

    public Board createBoard(Board board) {
        return boardRepository.save(board);
    }

    public Optional<Board> getByBoardId(Long boardId) {
        return boardRepository.findById(boardId);
    }

    public List<BoardColumn> getAllBoardColumns(Long boardId) {
        return boardRepository.findByBoardIdOrderByPosition(boardId);
    }

    public List<BoardCard> getBoardByCards(Long boardId,Long columnId) {
        return boardRepository.findByIdAndColumnOrderByPostion(boardId,columnId);
    }

    @Transactional
    public BoardCard addIssueToBoard(Long boardId, Long columnId, Long issueId) {

        Issue issue = issueRepo.findById(issueId).orElseThrow(()-> new RuntimeException("Issue with id: " + issueId+" not found"));

        boardCardRepository.findByIssueId(issueId).ifPresent(boardCard -> {boardCardRepository.delete(boardCard);});

        BoardColumn column=boardColumnRepository.findById(columnId).orElseThrow(()-> new RuntimeException("Column with id: " + columnId+" not found"));

        if(column.getWipLimit()!=null && column.getWipLimit()>0){
            long count=boardCardRepository.countByBoardIdAndColumnId(boardId,columnId);

            if(count>=column.getWipLimit()){
                throw new RuntimeException("Wip limit reached for column:"+column.getName());
            }


        }
        List<BoardCard> existing=boardCardRepository.findByBoardIdAndColumnOrderByPosition(boardId,columnId);

        int postion=existing.size();

        BoardCard boardCard=new BoardCard();
        boardCard.setBoardId(boardId);
        boardCard.setColumn(column);
        boardCard.setIssueId(issueId);
        boardCard.setPosition(postion);

//        if(column.getStatusKey()!=null){
//            issue.setIssueStatus(Enum.valueOf(IssueStatus.class,column.getStatusKey()));
//            issueRepo.save(issue);
//        }
        return boardCardRepository.save(boardCard);

    }
}
