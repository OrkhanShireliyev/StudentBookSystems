package com.example.springbootstudent.repository.jpa;

import com.example.springbootstudent.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AuthorRepositoryJpa extends JpaRepository<Author,Long> {



}
