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
    private final BoardConvertService boardConvertService;
    public BoardService(BoardRepository boardRepository, BoardConvertService boardConvertService) {
        this.boardRepository = boardRepository;   // @autowired 안쓰고 의존성 주입하는 방법 (생성자 주입 )
        this.boardConvertService = boardConvertService;
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
            BoardUpdateResponseDto dto = boardConvertService.convertToDto2(board);
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



    }


}



