package com.example.springbootstudent.repository.jpa;

import com.example.springbootstudent.model.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepositoryJpa extends JpaRepository<Book,Long> {
    List<Book> findBooksByCategoryName(String categoryName);

    Book findBookByName(String name);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,attributePaths = {"authors", "category"})
    @Query("select b from Book b join fetch b.authors and join fetch b.category")
    List<Book> findAllBooksWithAuthorsAndCategory();

    @EntityGraph(value = "book-authors-category-graphs", type = EntityGraph.EntityGraphType.FETCH,attributePaths = {"authors", "category"})
    List<Book> findAll();
}
