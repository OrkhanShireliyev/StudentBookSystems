package com.example.springbootstudent.repository.jpa;

import com.example.springbootstudent.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepositoryJpa extends JpaRepository<Book,Long> {
}
