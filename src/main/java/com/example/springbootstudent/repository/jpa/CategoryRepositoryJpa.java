package com.example.springbootstudent.repository.jpa;

import com.example.springbootstudent.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepositoryJpa extends JpaRepository<Category,Long> {
}
