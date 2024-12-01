package com.example.springbootstudent.dto;

import com.example.springbootstudent.model.Book;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorDTO {
    private String name;
    private String surname;
    private List<Book> books;

}
