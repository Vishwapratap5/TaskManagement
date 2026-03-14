package com.taskmanagement.taskmanagement.Controller;

import com.taskmanagement.taskmanagement.Entity.Board;
import com.taskmanagement.taskmanagement.Entity.BoardCard;
import com.taskmanagement.taskmanagement.Entity.BoardColumn;
import com.taskmanagement.taskmanagement.Service.BoardService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/create")
    public ResponseEntity<Board> createBoardCard(@RequestBody Board board){
        return new ResponseEntity<>(boardService.createBoard(board), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Board> findBoardById(@PathVariable("id") Long boardId){
        return new ResponseEntity<>(boardService.getByBoardId(boardId),HttpStatus.OK);
    }

    @GetMapping("/{id}/columns")
    public ResponseEntity<List<BoardColumn>> findBoardByColumnId(@PathVariable("id") Long boardId){
        return new ResponseEntity<>(boardService.getAllBoardColumns(boardId),HttpStatus.OK);
    }

    @GetMapping("/{id}/cards")
    public ResponseEntity<List<BoardCard>> findBoardById(@PathVariable("id") Long boardId, @PathVariable Long columnId){
        return new ResponseEntity<>(boardService.getBoardByCards(boardId,columnId),HttpStatus.OK);
    }

    @PostMapping("/{id}/card")
    public ResponseEntity<BoardCard> addCard(@PathVariable Long id,@RequestBody Map<String,Object> map){
        Long columnId=Long.valueOf(String.valueOf("columnId"));
        Long issueId=Long.valueOf(String.valueOf("issueId"));
        return new ResponseEntity<>(boardService.addIssueToBoard(id,columnId,issueId),HttpStatus.OK);
    }

    @PostMapping("/{id}/colum")
    public ResponseEntity<Board> addCard(@PathVariable Long id, @RequestBody BoardColumn boardColumn){
       boardColumn.setBoard(boardService.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Board not found")));
        return new ResponseEntity<>(boardService.createBoard(boardColumn.getBoard()),HttpStatus.OK);
    }


}
