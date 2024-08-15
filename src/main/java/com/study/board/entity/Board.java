package com.study.board.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Data
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String content;

    private String writer;

    @CreatedDate
    @Column(name = "write_time", updatable = false) //데이터베이스에 있는 write_time 과 매핑 , updatable = false 는 초기값 설정후 변경하지 않음)
    private LocalDateTime writeTime;

    @LastModifiedDate
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    public Board orElseThrow(Object o) {
        return null;
    }
}
