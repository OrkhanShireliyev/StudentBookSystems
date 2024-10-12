package com.example.springbootstudent.service.inter;

import com.example.springbootstudent.controller.request.BookRequest;
import com.example.springbootstudent.model.Author;
import com.example.springbootstudent.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookServiceInter {
    void save(BookRequest bookRequest,Long categoryId);

    List<BookRequest> getAll();

    BookRequest getBookById(Long id);

    void deleteBookById(Long id);

    void putById(Long id,String name,Category category,Author author);

    void updateName(Long id,String name);

    void updateBookCategory(Long id, Long categoryId);

    void updateBookAuthor(Long id, Author author);

}
