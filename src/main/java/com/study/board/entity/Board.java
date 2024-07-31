package com.study.board.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

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

    @CreatedDate  // 엔티티 생성될때의 시간을 자동으로 기록
    @Column  // 이 필드가 데이터베이스의 not null 컬럼임을 나타냄
    @Temporal(TemporalType.TIMESTAMP) // 이 필드가 타임스태프임을 나타냄
    private LocalDateTime writeTime;


}
