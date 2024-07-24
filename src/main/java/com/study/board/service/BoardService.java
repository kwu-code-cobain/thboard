package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    /**
     * 글 작성 처리
     * @param board 게시물 엔티티
     */
    public void write(Board board) {
        boardRepository.save(board); // 새로운 게시물을 저장
    }

    /**
     * 게시글 리스트 처리
     * @return 모든 게시물 리스트
     */
    public List<Board> boardlist() {
        return boardRepository.findAll(); // 모든 게시물을 가져옴
    }

    /*
     특정 게시글 불러오기
     @param id 게시물 ID
     @return 특정 게시물 엔티티
     */
    public Board boardView(Integer id) {
        return boardRepository.findById(id).orElse(null); // ID로 게시물 조회
    }

    /*
     특정 게시글 삭제
     @param id 게시물 ID
     */
    public void boardDelete(Integer id) {
        boardRepository.deleteById(id); // ID로 게시물 삭제
    }
}
