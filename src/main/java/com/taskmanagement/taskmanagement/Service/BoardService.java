package com.taskmanagement.taskmanagement.Service;

import com.taskmanagement.taskmanagement.Entity.Board;
import com.taskmanagement.taskmanagement.Entity.BoardCard;
import com.taskmanagement.taskmanagement.Entity.BoardColumn;
import com.taskmanagement.taskmanagement.Entity.Issue;
import com.taskmanagement.taskmanagement.Repository.BoardCardRepository;
import com.taskmanagement.taskmanagement.Repository.BoardColumnRepository;
import com.taskmanagement.taskmanagement.Repository.BoardRepository;
import com.taskmanagement.taskmanagement.Repository.IssueRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

    public Board getByBoardId(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(()->new EntityNotFoundException("Board not found"));
    }

    public List<BoardColumn> getAllBoardColumns(Long boardId) {
        return boardColumnRepository.findByBoard_BoardIdOrderByPosition(boardId);
    }

    public List<BoardCard> getBoardByCards(Long boardId,Long columnId) {
        return boardCardRepository.findByBoardIdAndColumn_IdOrderByPosition(boardId,columnId);
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
        List<BoardCard> existing=boardCardRepository.findByBoardIdAndColumn_IdOrderByPosition(boardId,columnId);

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


    @Transactional
    public void moveCard(Long boardId, Long columnId, Long cardId,int position,String performedBy) {

        BoardCard card=boardCardRepository.findById(cardId).orElseThrow(()->new RuntimeException("card not found..!"));
        BoardColumn from= card.getColumn();
        BoardColumn to= boardColumnRepository.findById(columnId).orElseThrow(()->new RuntimeException("card not found..!"));

        if(to.getWipLimit()!=null && to.getWipLimit()>0){
            Long count=boardCardRepository.countByBoardIdAndColumnId(boardId,columnId);
            if(Objects.equals(from.getId(),to.getId()) && count>=to.getWipLimit()){
                throw new RuntimeException("Wip limit reached for column:"+columnId);
            }
        }

        List<BoardCard> fromList=boardCardRepository.findByBoardIdAndColumn_IdOrderByPosition(boardId,from.getId());

        for(BoardCard c:fromList){
            if(c.getPosition()>card.getPosition()){
                c.setPosition(c.getPosition()-1);
                boardCardRepository.save(c);
            }
        }

        List<BoardCard> toList=boardCardRepository.findByBoardIdAndColumn_IdOrderByPosition(boardId,to.getId());
        for(BoardCard c:toList){
            if(c.getPosition()>=card.getPosition()){
                c.setPosition(c.getPosition()+1);
                boardCardRepository.save(c);
            }
        }

        card.setColumn(to);
        card.setPosition(card.getPosition());
        boardCardRepository.save(card);

        issueRepo.findById(card.getIssueId()).ifPresent(issue->{
            if(to.getStatusKey()!=null){
                issue.setIssueStatus(to.getStatusKey());
                issueRepo.save(issue);
            }
        });
    }

    @Transactional
    public void recordColumn(Long boardId, Long columnId, List<Long> orderedByCardId) {
        int position=0;
        for(Long id:orderedByCardId){
            BoardCard card=boardCardRepository.findById(id).orElseThrow(()->new RuntimeException("card not found..!"));
            card.setPosition(position++);
            boardCardRepository.save(card);
        }
    }

    public Optional<Board> findById(Long id){
        return boardRepository.findById(id);
    }
    @Transactional
    public void startSprint(Long sprintId){

    }


    @Transactional
    public void completeSprint(Long sprintId){

    }
}
