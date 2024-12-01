package com.example.springbootstudent.service.inter;

import com.example.springbootstudent.controller.request.BookRequest;
import com.example.springbootstudent.dto.AuthorDTO;
import com.example.springbootstudent.dto.BookDTO;
import com.example.springbootstudent.model.Author;
import com.example.springbootstudent.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookServiceInter {
    void save(BookRequest bookRequest,Long categoryId, List<Long> authorId);

    List<BookDTO> getAll();

    List<BookDTO> findAll();

    List<BookDTO> getBookByCategory(String categoryName);

    BookDTO getBookById(Long id);

    List<AuthorDTO> getAuthorByBookName(String bookName);

    void deleteBookById(Long id);

    void putById(Long id, String name,String year,double price,int count, Long categoryId, List<Long> authorsId);

    void updateName(Long id,String name);

    void updateBookCategory(Long id, Long categoryId);

    void updateBookAuthor(Long id, List<Long> authorsId);

}
