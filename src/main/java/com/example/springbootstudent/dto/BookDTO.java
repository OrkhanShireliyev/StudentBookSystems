package com.example.springbootstudent.dto;

import com.example.springbootstudent.model.Author;
import com.example.springbootstudent.model.Category;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDTO {
    private String name;
    private String year;
    private double price;
    private int count;
    private Category category;
    private List<Author> authors;

}
