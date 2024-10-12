package com.example.springbootstudent.service.impl;

import com.example.springbootstudent.controller.request.BookRequest;
import com.example.springbootstudent.exception.NotFoundException;
import com.example.springbootstudent.model.Author;
import com.example.springbootstudent.model.Book;
import com.example.springbootstudent.model.Category;
import com.example.springbootstudent.repository.jpa.AuthorRepositoryJpa;
import com.example.springbootstudent.repository.jpa.BookRepositoryJpa;
import com.example.springbootstudent.repository.jpa.CategoryRepositoryJpa;
import com.example.springbootstudent.service.inter.BookServiceInter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class BookServiceImpl implements BookServiceInter {
    private final BookRepositoryJpa bookRepositoryJpa;
    private final CategoryRepositoryJpa categoryRepositoryJpa;

    private final AuthorRepositoryJpa authorRepositoryJpa;

    public BookServiceImpl(BookRepositoryJpa bookRepositoryJpa,
                           CategoryRepositoryJpa categoryRepositoryJpa, AuthorRepositoryJpa authorRepositoryJpa) {
        this.bookRepositoryJpa = bookRepositoryJpa;
        this.categoryRepositoryJpa = categoryRepositoryJpa;
        this.authorRepositoryJpa = authorRepositoryJpa;
    }

//    @Override
//    public void save(BookRequest bookRequest,Long categoryId,String authorName,String authorSurname) {
//        Category category=categoryRepositoryJpa.findById(categoryId).get();
//        if (category==null){
//            throw new NotFoundException("Not found category with id="+categoryId);
//        }
//        Set<Author> authors= (Set<Author>) authorRepositoryJpa.findAll();//10 m 2 kit yazib bunu 8 ayr;d;
//
//
//
//        if (authors==null){
//            throw new NotFoundException("Not found authors!");
//        }
//
//        Book book=Book.builder()
//                .name(bookRequest.getName())
//                .year(bookRequest.getYear())
//                .category(category)
//                .authors(authors)
//                .build();
//
//        bookRepositoryJpa.save(book);
//    }

    @Override
    public void save(BookRequest bookRequest, Long categoryId) {

    }

    @Override
    public List<BookRequest> getAll() {
        return null;
    }

    @Override
    public BookRequest getBookById(Long id) {
        return null;
    }

    @Override
    public void deleteBookById(Long id) {

    }

    @Override
    public void putById(Long id, String name, Category category, Author author) {

    }

    @Override
    public void updateName(Long id, String name) {

    }

    @Override
    public void updateBookCategory(Long id, Long categoryId) {
        Book book=bookRepositoryJpa.findById(id).get();

        Category category= categoryRepositoryJpa.findById(categoryId).get();

        book.setCategory(category);
    }

    @Override
    public void updateBookAuthor(Long id, Author author) {

    }
}
