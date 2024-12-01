package com.example.springbootstudent.service.impl;

import com.example.springbootstudent.controller.request.AuthorRequest;
import com.example.springbootstudent.dto.AuthorDTO;
import com.example.springbootstudent.exception.NotFoundException;
import com.example.springbootstudent.model.Author;
import com.example.springbootstudent.model.Book;
import com.example.springbootstudent.repository.jpa.AuthorRepositoryJpa;
import com.example.springbootstudent.repository.jpa.BookRepositoryJpa;
import com.example.springbootstudent.service.inter.AuthorServiceInter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AuthorServiceImpl implements AuthorServiceInter {

    private final AuthorRepositoryJpa authorRepositoryJpa;
    private final BookRepositoryJpa bookRepositoryJpa;

    public AuthorServiceImpl(AuthorRepositoryJpa authorRepositoryJpa,
                             BookRepositoryJpa bookRepositoryJpa) {
        this.authorRepositoryJpa = authorRepositoryJpa;
        this.bookRepositoryJpa = bookRepositoryJpa;
    }

    @Override
    public void save(AuthorRequest authorRequest, List<Long> bookId) {
        List<Book>books=bookRepositoryJpa.findAllById(bookId);
        if (books==null){
            throw new NotFoundException("Not found books!");
        }

        try {
            Author author = new Author();
            author.setName(authorRequest.getName());
            author.setSurname(authorRequest.getSurname());
            author.setBooks(books);

            authorRepositoryJpa.save(author);
            log.info("Successfully created {}",author);
        }catch (Exception e){
            log.error("Error happened at trying creating author!");
        }
    }

    @Override
    public void saveWithoutBook(AuthorRequest authorRequest) {
        try {
            Author author = new Author();
            author.setName(authorRequest.getName());
            author.setSurname(authorRequest.getSurname());

            authorRepositoryJpa.save(author);
            log.info("Successfully created {}",author);
        }catch (Exception e){
            log.error("Error happened at trying creating author!");
        }
    }

    @Override
    public List<AuthorDTO> getAll() {
        List<Author> authors=authorRepositoryJpa.findAll();
        if (authors==null){
            throw new NotFoundException("Not found authors!");
        }
        List<AuthorDTO>authorDTOS =new ArrayList<>();
        AuthorDTO authorDTO;

        try{
            for (Author author:authors){
                authorDTO=AuthorDTO.builder()
                .name(author.getName())
                .surname(author.getSurname())
                .books(author.getBooks())
                .build();

            authorDTOS.add(authorDTO);

            log.info("Successfully retrieved {}",authorDTOS);
            }
        }catch (Exception e){
            log.error("Error happened at trying retrieving author!");
        }
        return authorDTOS;
    }

    @Override
    public AuthorDTO getAuthorById(Long id) {
        Author author=authorRepositoryJpa.findById(id).get();
        if (author==null){
            throw new NotFoundException("Not found author!");
        }
        AuthorDTO authorDTO=new AuthorDTO();

        try{
            authorDTO=AuthorDTO.builder()
                    .name(author.getName())
                    .surname(author.getSurname())
                    .books(author.getBooks())
                    .build();
            log.info("Successfully retrieved {}",authorDTO);
        }catch (Exception e){
            log.error("Error happened at trying retrieving author!");
        }
        return authorDTO;
    }

    @Override
    public List<AuthorDTO> getAuthorByBookName(String bookName) {
        Book bookByName=bookRepositoryJpa.findBookByName(bookName);

        if (bookByName==null){
            throw new NotFoundException("Not found author!");
        }

        List<Author> authorByBooks=authorRepositoryJpa.findAuthorsByBooks(bookByName);
        System.out.println("authorByBooks-u kechdi");

        if (authorByBooks==null){
            System.out.println("authorByBooks==null-a daxil oldu");
            throw new NotFoundException("Not found author!");
        }

        AuthorDTO authorDTO=new AuthorDTO();
        List<AuthorDTO> authorDTOS=new ArrayList<>();

        try{
        for (Author author:authorByBooks) {
            authorDTO.setName(author.getName());
            authorDTO.setSurname(author.getSurname());
            authorDTO.setBooks(author.getBooks());

            authorDTOS.add(authorDTO);
            log.info("Successfully retrieved {}",authorDTOS);
        }
        }catch (Exception e){
                log.error("Error happened at trying retrieving author!");
            }
        return authorDTOS;
    }

    @Override
    public void deleteAuthorById(Long id) {
        Author author=authorRepositoryJpa.findById(id).get();
        if (author==null){
            throw new NotFoundException("Not found author!");
        }

        try {
            authorRepositoryJpa.deleteById(id);
            log.info("Successfully deleted {}",author);
        }catch (Exception e){
            log.error("Error happened at trying deleting author!");
        }
    }

    @Override
    public void putById(Long id, String name, String surname, List<Long> bookId) {
        Author authorById=authorRepositoryJpa.findById(id).get();
        if (authorById==null){
            throw new NotFoundException("Not found author!");
        }

        List<Book> booksById=bookRepositoryJpa.findAllById(bookId);
        if (booksById==null){
            throw new NotFoundException("Not found books by id="+bookId);
        }

        try{
            authorById.setName(name);
            authorById.setSurname(surname);
            authorById.setBooks(booksById);
            authorRepositoryJpa.save(authorById);
            log.info("Successfully updated {}",authorById);
        }catch (Exception e){
            log.error("Error happened at trying updating author!");
        }
    }

    @Override
    public void updateName(Long id, String name) {
        Author author=authorRepositoryJpa.findById(id).get();
        if (author==null){
            throw new NotFoundException("Not found author!");
        }

        try {
            author.setName(name);
            authorRepositoryJpa.save(author);
            log.info("Successfully updated {}",author);
        }catch (Exception e){
            log.error("Error happened at trying updating author!");
        }
    }

    @Override
    public void updateSurname(Long id, String surname) {
        Author author=authorRepositoryJpa.findById(id).get();
        if (author==null){
            throw new NotFoundException("Not found author!");
        }

        try {
            author.setSurname(surname);
            authorRepositoryJpa.save(author);
            log.info("Successfully updated {}",author);
        }catch (Exception e){
            log.error("Error happened at trying updating author!");
        }
    }

    @Override
    public void updateAuthorBook(Long id, List<Long> bookId) {
        Author authorById=authorRepositoryJpa.findById(id).get();
        if (authorById==null){
            throw new NotFoundException("Not found author!"+id);
        }

        List<Book> booksById=bookRepositoryJpa.findAllById(bookId);
        if (booksById==null){
            throw new NotFoundException("Not found books by id="+bookId);
        }

        try {
            authorById.setBooks(booksById);
            log.info("Successfully updated {}",authorById);
        }catch (Exception e){
            log.error("Error happened at trying updating author!");
        }
    }
}
