package com.study.board.service;

import com.study.board.dto.BoardListResponseDto;
import com.study.board.dto.BoardRequestDto;
import com.study.board.dto.BoardUpdateResponseDto;
import com.study.board.dto.BoardWriteResponseDto;
import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;   // @autowired 안쓰고 의존성 주입하는 방법 (생성자 주입 )

    }

    /**
     * 글 작성 처리
     */
    public BoardWriteResponseDto write(BoardRequestDto boardRequestDto) {
        Board board = new Board();
        board.setWriteTime(LocalDateTime.now());
        boardRepository.save(board);
        // 새로운 게시물을 저장

        return new BoardWriteResponseDto(board.getId(), board.getTitle(), board.getContent(), board.getWriter(), board.getWriteTime());
    }

    /**
     * 게시글 리스트 처리
     *
     * @return 모든 게시물 리스트
     */
//    public List<Board> boardlist() {
//        return boardRepository.findAll();
//
//        // 모든 게시물을 가져옴
//    }
    public BoardListResponseDto getAllBoardDtos() {
        List<Board> boardList = boardRepository.findAll();
        if (boardList.isEmpty()) {
            throw new NullPointerException("게시물이 존재하지 않습니다");
        }
        BoardListResponseDto response = new BoardListResponseDto();
        List<BoardUpdateResponseDto> list = response.getBoardList();
//        List<BoardUpdateResponseDto> list = new ArrayList<>();
        for (Board board : boardList) {
            list.add(new BoardUpdateResponseDto(board.getId(), board.getTitle(), board.getContent(), board.getWriter(), board.getWriteTime(), board.getUpdateTime() ));
//            BoardUpdateResponseDto dto = boardConvertService.convertToDto2(board);
//            list.add(dto);
        }
        response.setBoardList(list);
        return response;
        }
    /*
     특정 게시글 불러오기
     */
    public BoardUpdateResponseDto boardViewById(Integer id) {
        Board board = boardRepository.findById(id).orElseThrow(()->new NullPointerException("게시물이 존재하지 않습니다."));
        return new BoardUpdateResponseDto(board.getId(), board.getTitle(), board.getContent(), board.getWriter(), board.getWriteTime(), board.getUpdateTime());
    }

    public BoardUpdateResponseDto boardViewByWriter(String writer) {
        Board board = boardRepository.findByWriter(writer);
        if (board == null) {
            throw new NullPointerException("게시물이 존재하지 않습니다");
        }
        return new BoardUpdateResponseDto(board.getId(), board.getTitle(), board.getContent(), board.getWriter(), board.getWriteTime(), board.getUpdateTime());
    }

    public BoardUpdateResponseDto boardViewByTitle(String title) {
        Board board = boardRepository.findByTitle(title);
        if (board == null) {
            throw new NullPointerException("게시물이 존재하지 않습니다");
        }
        return new BoardUpdateResponseDto(board.getId(), board.getTitle(), board.getContent(), board.getWriter(), board.getWriteTime(), board.getUpdateTime());
    }


    /*
     특정 게시글 삭제
     */
    public void boardDelete(Integer id) {
        boardRepository.deleteById(id); // ID로 게시물 삭제
    }


//    public void updateBoardDetails(Board boardTemp, BoardRequestDto boardRequestDto) {
//        boardTemp.setTitle(boardRequestDto.getTitle()); // 제목 수정
//        boardTemp.setContent(boardRequestDto.getContent());
//        boardTemp.setWriter(boardRequestDto.getWriter());// 내용 수정
//        boardTemp.setUpdateTime(LocalDateTime.now());
//        boardRepository.save(boardTemp); // 수정된 게시물 저장
//
//    }
    public BoardUpdateResponseDto updateBoardDetails(int id, BoardRequestDto boardRequestDto) {
        Board board = boardRepository.findById(id).orElseThrow(()->new NullPointerException("게시물이 존재하지 않습니다."));
        board.setTitle(boardRequestDto.getTitle()); // 제목 수정
        board.setContent(boardRequestDto.getContent());
        board.setWriter(boardRequestDto.getWriter());// 내용 수정
        board.setUpdateTime(LocalDateTime.now());

        return new BoardUpdateResponseDto(board.getId(), board.getTitle(), board.getContent(), board.getWriter(), board.getWriteTime(), board.getUpdateTime());
    }
}





