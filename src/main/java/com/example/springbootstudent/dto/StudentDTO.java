package com.example.springbootstudent.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentDTO {
    private String name;
    private String surname;
    private int age;
    private int score;
    private String groupName;

    private String imagePath;

    public StudentDTO() {
    }

    public StudentDTO(String name, String surname, int age, int score, String groupName, String imagePath) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.score = score;
        this.groupName = groupName;
        this.imagePath = imagePath;
    }
}
