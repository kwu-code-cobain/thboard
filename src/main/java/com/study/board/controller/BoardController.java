package com.study.board.controller;

import com.study.board.dto.BoardListResponseDto;
import com.study.board.dto.BoardRequestDto;
import com.study.board.dto.BoardUpdateResponseDto;
import com.study.board.dto.BoardWriteResponseDto;
import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // @Controller 대신 @RestController 사용
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;   // @autowired 안쓰고 의존성 주입하는 방법 (생성자 주입 )
    }


    @GetMapping("/list")
    public ResponseEntity<?> boardList() {
        try {
            return ResponseEntity.ok(boardService.getAllBoardDtos());
        }catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    /*
     새로운 게시물 생성
     */
    @PostMapping("/write")
    @ResponseBody
    public ResponseEntity<?> boardWrite(@RequestBody BoardRequestDto boardRequestDto) {
        BoardWriteResponseDto response = boardService.write(boardRequestDto);
        return ResponseEntity.ok(response);
    }
    /*
     특정 게시물 상세 페이지를 반환
     */
    @GetMapping("/viewbyid")
    public ResponseEntity<?> boardViewById(@RequestParam(name = "id") Integer id) {
        try {
            BoardUpdateResponseDto response = boardService.boardViewById(id);
            return ResponseEntity.ok(response);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/viewbywriter")
    public ResponseEntity<?> boardViewByWriter(@RequestParam(name = "writer") String writer) {
        try {
            BoardUpdateResponseDto response = boardService.boardViewByWriter(writer);
            return ResponseEntity.ok(response);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/viewbytitle")
    public ResponseEntity<?> boardViewByTitle(@RequestParam(name = "title") String title) {
        try {
            BoardUpdateResponseDto response = boardService.boardViewByTitle(title);
            return ResponseEntity.ok(response);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    /*
     게시물 삭제 처리
     @return 성공 메시지
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> boardDelete(@RequestParam(name = "id") int id) {
        boardService.boardDelete(id); // id로 게시물 삭제
        return ResponseEntity.ok("Deleted"); // 성공 메시지 반환
    }

    /*
        게시물 수정 처리
     */
    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<?> boardUpdate(@RequestParam(name = "id") Integer id, @RequestBody BoardRequestDto boardRequestDto) {
//        Board board = boardService.boardViewById(id); // 기존 게시물 조회 - > boardTemp 할당
//        if (board == null) {       // 수정 완료 ->   boardTemp == null 이면 오류 반환 , else 안돌리게끔
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("게시판을 찾을 수 없습니다."); //
//        }
//        boardService.updateBoardDetails(id, boardRequestDto); //updateBoardDetails 를 선언 하여 Service 에 옮김.
//        return ResponseEntity.ok(boardConvertService.convertToDto2(board)); // 성공 메시지 반환
        try {
            BoardUpdateResponseDto response = boardService.updateBoardDetails(id, boardRequestDto);
            return ResponseEntity.ok(response);
        } catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}

