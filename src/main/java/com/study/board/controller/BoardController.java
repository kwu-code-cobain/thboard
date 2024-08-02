package com.study.board.controller;

import com.study.board.dto.BoardDto;
import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController // @Controller 대신 @RestController 사용
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;   // @autowired 안쓰고 의존성 주입하는 방법 (생성자 주입 )
    }


    @GetMapping("/list")
    public ResponseEntity<List<BoardDto>> boardList() {
        List<Board> boardList = boardService.boardlist();
        // 빈 DTO 리스트를 생성
        List<BoardDto> list = new ArrayList<>();
        // for 문을 사용하여 각 게시물을 DTO로 변환하여 리스트에 추가
        for (Board board : boardList) {
            BoardDto dto = convertToDto(board);
            list.add(dto);
        }

        return ResponseEntity.ok(list); // 게시물 목록을 JSON으로 반환
    }


    /*
     새로운 게시물 생성
     */
    @PostMapping("/write")
    @ResponseBody
    public ResponseEntity<BoardDto> boardWrite(@RequestBody BoardDto boardDto) {
        Board board = convertToEntity(boardDto);
        boardService.write(board);
        return ResponseEntity.ok(convertToDto(board));
    }


    /*
     특정 게시물 상세 페이지를 반환
     */


    @GetMapping("/viewbyid")
    public ResponseEntity<?> boardViewById(@RequestParam(name = "id") Integer id) {
        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다."); // 400 Bad Request 반환
        }
        Board board = boardService.boardViewById(id);

        if (board == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("게시판을 찾을 수 없습니다."); // 404 Not Found 반환
        }
        return ResponseEntity.ok(convertToDto(board)); // 게시물 객체를 JSON으로 반환
    }

    @GetMapping("/viewbywriter")
    public ResponseEntity<?> boardViewByWriter(@RequestParam(name = "writer") String writer) {
        if (writer == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다."); // 400 Bad Request 반환
        }
        Board board = boardService.boardViewByWriter(writer); //리스트 반환 되게끔
        if (board == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("게시판을 찾을 수 없습니다."); // 404 Not Found 반환
        }
        return ResponseEntity.ok(convertToDto(board)); // 게시물 객체를 JSON으로 반환
    }

    @GetMapping("/viewbytitle")
    public ResponseEntity<?> boardViewByTitle(@RequestParam(name = "title") String title) {
        if (title == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다."); // 400 Bad Request 반환, httpstatus, body (메세지) 수정
        }
        Board board = boardService.boardViewByTitle(title); //리스트 반환 되게끔
        if (board == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("게시판을 찾을 수 없습니다."); // 404 Not Found 반환
        }
        return ResponseEntity.ok(convertToDto(board)); // 게시물 객체를 JSON으로 반환
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
    public ResponseEntity<?> boardUpdate(@RequestParam(name = "id") Integer id, @RequestBody BoardDto boardDto) {
        Board board = boardService.boardViewById(id); // 기존 게시물 조회 - > boardTemp 할당
        if (board == null) {       // 수정 완료 ->   boardTemp == null 이면 오류 반환 , else 안돌리게끔
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("게시판을 찾을 수 없습니다."); //
        }
        boardService.updateBoardDetails(board, boardDto); //updateBoardDetails 를 선언 하여 Service 에 옮김.
        return ResponseEntity.ok(convertToDto(board)); // 성공 메시지 반환
    }

    private BoardDto convertToDto(Board board) {
        // 엔티티를 DTO로 변환
        BoardDto boardDto = new BoardDto();
        boardDto.setWriteTime(board.getWriteTime());
        boardDto.setUpdateTime(board.getUpdateTime());
        boardDto.setId(board.getId());
        boardDto.setWriter(board.getWriter());
        boardDto.setTitle(board.getTitle());
        boardDto.setContent(board.getContent());
        return boardDto;
    }

    private Board convertToEntity(BoardDto boardDto) {
        // DTO를 엔티티로 변환
        Board board = new Board();
        board.setId(boardDto.getId());
        board.setWriter(boardDto.getWriter());
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        return board;
    }
    }

