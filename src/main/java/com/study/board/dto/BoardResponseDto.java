package com.study.board.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class BoardResponseDto {
    private Integer id;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime writeTime;


    //dto request 하나 만들기.


}
