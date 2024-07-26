package com.study.board.dto;

import com.study.board.entity.Board;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class BoardDto {
    private Integer id;
    private String title;
    private String content;
    private String writer;
    private String writeTime;


}
