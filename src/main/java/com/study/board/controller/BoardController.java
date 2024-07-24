package com.study.board.controller;

import com.study.board.dto.BoardDto;
import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController // @Controller 대신 @RestController 사용
@RequestMapping("/boards")
public class BoardController {

    @Autowired
    private BoardService boardService; // BoardService를 자동으로 주입받음

    /*
     모든 게시물 가져오기
     @return 모든 게시물의 리스트
     */
    @GetMapping("/list")
    public ResponseEntity<List<BoardDto>> boardlist() {
        // 서비스에서 모든 게시물을 가져와서 DTO 리스트로 변환하여 반환
        List<BoardDto> list = boardService.boardlist().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list); // 게시물 목록을 JSON으로 반환
    }

    /*
     새로운 게시물 생성
     @param boardDto 게시물 DTO
     @return 생성된 게시물의 DTO
     */
    @PostMapping("/writepro")
    public ResponseEntity<String> boardwritepro(@RequestBody BoardDto boardDto) {
        // DTO를 엔티티로 변환하고 서비스에 전달하여 저장된 결과를 다시 DTO로 변환하여 반환
        Board board = convertToEntity(boardDto);
        boardService.write(board); // 전달받은 Board 객체를 저장
        return ResponseEntity.ok("Success"); // 성공 메시지 반환
    }

    /*
     특정 게시물 상세 페이지를 반환
     @return 게시물 DTO
     */
    @GetMapping("/view")
    public ResponseEntity<BoardDto> boardView(@RequestParam(name = "id") Integer id) {
        Board board = boardService.boardView(id); // id로 조회한 게시물
        if (board != null) {
            return ResponseEntity.ok(convertToDto(board)); // 게시물 객체를 JSON으로 반환
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found 반환
        }
    }

    /*
     게시물 삭제 처리
     @return 성공 메시지
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> boardDelete(@RequestParam("id") int id) {
        boardService.boardDelete(id); // id로 게시물 삭제
        return ResponseEntity.ok("Deleted"); // 성공 메시지 반환
    }

    /*
     게시물 수정 처리
     @param id 게시물 ID
     @return 성공 메시지
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<String> boardUpdate(@PathVariable("id") Integer id, @RequestBody BoardDto boardDto) {
        Board boardTemp = boardService.boardView(id); // 기존 게시물 조회
        if (boardTemp != null) {
            boardTemp.setTitle(boardDto.getTitle()); // 제목 수정
            boardTemp.setContent(boardDto.getContent()); // 내용 수정
            boardService.write(boardTemp); // 수정된 게시물 저장
            return ResponseEntity.ok("Updated"); // 성공 메시지 반환
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found 반환
        }
    }

    /*
     Board 엔티티를 DTO로 변환
     @return 게시물 DTO
     */
    private BoardDto convertToDto(Board board) {
        // 엔티티를 DTO로 변환
        BoardDto boardDto = new BoardDto();
        boardDto.setId(board.getId());
        boardDto.setTitle(board.getTitle());
        boardDto.setContent(board.getContent());
        return boardDto;
    }

    /*
     Board DTO를 엔티티로 변환
     @return 게시물 엔티티
     */
    private Board convertToEntity(BoardDto boardDto) {
        // DTO를 엔티티로 변환
        Board board = new Board();
        board.setId(boardDto.getId());
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        return board;
    }
}
