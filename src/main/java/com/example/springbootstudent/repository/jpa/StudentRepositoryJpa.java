package com.example.springbootstudent.repository.jpa;

import com.example.springbootstudent.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepositoryJpa extends JpaRepository<Student,Long> {

}
