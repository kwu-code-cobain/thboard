package com.study.board.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardWriteResponseDto {
    private Integer id;
    private String title;
    private String content;
    private String writer;

    private LocalDateTime writeTime;

}
