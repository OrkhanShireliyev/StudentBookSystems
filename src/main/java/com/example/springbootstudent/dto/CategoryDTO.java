package com.example.springbootstudent.dto;

import com.example.springbootstudent.model.Book;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO {
    private String name;
    private List<Book> books;

}
