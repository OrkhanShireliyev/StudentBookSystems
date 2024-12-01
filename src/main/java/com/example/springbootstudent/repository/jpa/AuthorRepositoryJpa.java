package com.example.springbootstudent.repository.jpa;

import com.example.springbootstudent.model.Author;
import com.example.springbootstudent.model.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepositoryJpa extends JpaRepository<Author,Long> {

//    List<Author> findAuthorByBooks(Long id);

//    @Query(value ="select * from book_author where book_id=:book_id ",nativeQuery = true)
//    List<Author> findAuthorsByBooks(Book book);

//    @Query(value ="select * from book_author where book_id=:book_id ",nativeQuery = true)
//    List<Author> findMyAuthors(@Param("book_id") Long book_id);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,value ="author-book-graphs")
//    @Query(value ="select a from Author join fetch a.")
    List<Author> findAuthorsByBooks(Book book);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,value ="author-book-graphs")
    List<Author> findAll();
}
