package com.study.board.service;

import com.study.board.dto.BoardRequestDto;
import com.study.board.dto.BoardUpdateResponseDto;
import com.study.board.dto.BoardWriteResponseDto;
import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardConvertService {

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

