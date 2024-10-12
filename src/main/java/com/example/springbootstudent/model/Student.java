package com.example.springbootstudent.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private int age;
    private int score;
    private String groupName;

    private String imagePath;

    public Student(String name, String surname, int age, int score, String groupName) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.score = score;
        this.groupName = groupName;
    }

    public Student(Long id, String name, String surname, int age, int score, String groupName) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.score = score;
        this.groupName = groupName;
    }

    public Student(Long id, String name, String surname, int age, int score, String groupName, String imagePath) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.score = score;
        this.groupName = groupName;
        this.imagePath = imagePath;
    }

    public Student() {

    }
}
