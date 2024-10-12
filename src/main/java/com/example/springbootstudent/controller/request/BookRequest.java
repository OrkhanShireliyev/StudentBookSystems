package com.example.springbootstudent.controller.request;

import com.example.springbootstudent.model.Author;
import com.example.springbootstudent.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {
    private Long id;
    private String name;
    private String year;
    private Category category;
    private Set<Author> authors;
}
