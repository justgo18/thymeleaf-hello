package com.testborad.youtubeborad.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity            //테이블을 의미함
@Data
public class Board {

    //mysql 스키마 속 작성 형식에 맞게 필드 형성
    @Id //프라이머리키
    @GeneratedValue(strategy = GenerationType.IDENTITY ) //전략을 어떻게할껀지 mysql mariadb에서 는 identity사용
    private Integer id; // int

    private String title;

    private String content;

    private String filename;

    private String filepath;


}
