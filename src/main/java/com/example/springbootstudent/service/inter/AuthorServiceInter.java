package com.example.springbootstudent.service.inter;

import com.example.springbootstudent.controller.request.AuthorRequest;
import com.example.springbootstudent.dto.AuthorDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public interface AuthorServiceInter  {

    void save(AuthorRequest authorRequest,List<Long> bookId);

    void saveWithoutBook(@RequestBody AuthorRequest authorRequest);

    List<AuthorDTO> getAll();

    AuthorDTO getAuthorById(Long id);

    List<AuthorDTO> getAuthorByBookName(String bookName);

    void deleteAuthorById(Long id);

    void putById(Long id, String name, String surname, List<Long> bookId);

    void updateName(Long id,String name);

    void updateSurname(Long id, String name);

    void updateAuthorBook(Long id, List<Long> bookId);
}
