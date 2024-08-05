package com.study.board.service;

import com.study.board.dto.BoardRequestDto;
import com.study.board.dto.BoardUpdateResponseDto;
import com.study.board.dto.BoardWriteResponseDto;
import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;   // @autowired 안쓰고 의존성 주입하는 방법 (생성자 주입 )
    }

    /**
     글 작성 처리
     */
    public void write(Board board) {
        board.setWriteTime(LocalDateTime.now());
        boardRepository.save(board); // 새로운 게시물을 저장
    }


    /**
     * 게시글 리스트 처리
     *
     * @return 모든 게시물 리스트
     */
    public List<Board> boardlist() {
        return boardRepository.findAll();

        // 모든 게시물을 가져옴
    }
    public List<BoardUpdateResponseDto> getAllBoardDtos() {
        List<Board> boardList = boardlist();
        List<BoardUpdateResponseDto> list = new ArrayList<>();
        for (Board board : boardList) {
            BoardUpdateResponseDto dto = convertToDto2(board);
            list.add(dto);
        }
        return list;
        }
    /*
     특정 게시글 불러오기
     */
    public Board boardViewById(Integer id) {
        return boardRepository.findById(id).orElse(null);
    }

    public Board boardViewByWriter(String writer) {
        return boardRepository.findByWriter(writer);

    }

    public Board boardViewByTitle(String title) {

        return boardRepository.findByTitle(title);
    }

    /*
     특정 게시글 삭제
     */
    public void boardDelete(Integer id) {
        boardRepository.deleteById(id); // ID로 게시물 삭제
    }


    public void updateBoardDetails(Board boardTemp, BoardRequestDto boardRequestDto) {
        boardTemp.setTitle(boardRequestDto.getTitle()); // 제목 수정
        boardTemp.setContent(boardRequestDto.getContent());
        boardTemp.setWriter(boardRequestDto.getWriter());// 내용 수정
        boardTemp.setUpdateTime(LocalDateTime.now());
        boardRepository.save(boardTemp); // 수정된 게시물 저장
    }



    public BoardWriteResponseDto convertToDto(Board board) {
        // 엔티티를 DTO로 변환
        BoardWriteResponseDto boardWriteResponseDto = new BoardWriteResponseDto();
        boardWriteResponseDto.setWriteTime(board.getWriteTime());
        boardWriteResponseDto.setId(board.getId());
        boardWriteResponseDto.setWriter(board.getWriter());
        boardWriteResponseDto.setTitle(board.getTitle());
        boardWriteResponseDto.setContent(board.getContent());
        return boardWriteResponseDto;
    }

    public BoardUpdateResponseDto convertToDto2(Board board) {

        BoardUpdateResponseDto boardUpdateResponseDto = new BoardUpdateResponseDto();
        boardUpdateResponseDto.setId(board.getId());
        boardUpdateResponseDto.setTitle(board.getTitle());
        boardUpdateResponseDto.setContent(board.getContent());
        boardUpdateResponseDto.setWriter(board.getWriter());
        boardUpdateResponseDto.setWriteTime(board.getWriteTime());
        boardUpdateResponseDto.setUpdateTime(board.getUpdateTime());
        return boardUpdateResponseDto;
    }

    public Board convertToEntity(BoardRequestDto boardRequestDto) {
        // DTO를 엔티티로 변환
        Board board = new Board();
        board.setWriter(boardRequestDto.getWriter());
        board.setTitle(boardRequestDto.getTitle());
        board.setContent(boardRequestDto.getContent());
        return board;
    }

}



