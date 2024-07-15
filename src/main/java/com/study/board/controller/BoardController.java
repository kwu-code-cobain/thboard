package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // @Controller 대신 @RestController 사용
public class BoardController {

    @Autowired
    private BoardService boardService; // BoardService를 자동으로 주입받음

    // 게시물 작성 처리를 하는 메서드
    @PostMapping("/board/writepro")
    public ResponseEntity<String> boardwritepro(@RequestBody Board board) {
        boardService.write(board); // 전달받은 Board 객체를 저장
        return ResponseEntity.ok("Success"); // 성공 메시지 반환
    }

    // 게시물 목록 페이지를 반환하는 메서드
    @GetMapping("/board/list")
    public ResponseEntity<List<Board>> boardlist() {
        List<Board> list = boardService.boardlist(); // 게시물 목록 조회
        return ResponseEntity.ok(list); // 게시물 목록을 JSON으로 반환
    }

    // 특정 게시물 상세 페이지를 반환하는 메서드
    @GetMapping("/board/view")
    public ResponseEntity<Board> boardView(@RequestParam(name = "id") Integer id) {
        Board board = boardService.boardView(id); // id로 조회한 게시물
        if (board != null) {
            return ResponseEntity.ok(board); // 게시물 객체를 JSON으로 반환
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found 반환
        }
    }

    // 게시물 삭제 처리를 하는 메서드
    @DeleteMapping("/board/delete")
    public ResponseEntity<String> boardDelete(@RequestParam("id") int id) {
        boardService.boardDelete(id); // id로 게시물 삭제
        return ResponseEntity.ok("Deleted"); // 성공 메시지 반환
    }

    // 게시물 수정 처리를 하는 메서드
    @PutMapping("/board/update/{id}")
    public ResponseEntity<String> boardUpdate(@PathVariable("id") Integer id, @RequestBody Board board) {
        Board boardTemp = boardService.boardView(id); // 기존 게시물 조회
        if (boardTemp != null) {
            boardTemp.setTitle(board.getTitle()); // 제목 수정
            boardTemp.setContent(board.getContent()); // 내용 수정
            boardService.write(boardTemp); // 수정된 게시물 저장
            return ResponseEntity.ok("Updated"); // 성공 메시지 반환
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found 반환
        }
    }
}
