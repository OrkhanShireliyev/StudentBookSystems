package com.example.springbootstudent.repository.inter;

import com.example.springbootstudent.model.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepositoryInter {

    //void save(Student student);
    int save(Student student);

    List<Student> getAll();

    Student getStudentById(Long id);

    void deleteStudentById(Long id);

    void putById(Long id,String name,String surname,int age,int score,String groupName);

    void updateName(Long id,String name);

    void updateSurname(Long id,String surname);

    void updateAge(Long id,int age);

    void updateGroupName(Long id,String groupName);


    //id ad soyad yas bal qrup adi password

    //daxile edilen//ad soyad yas bal qrup adi password

    //ekrana qaytarilan// ad soyad yas bal qrup adi
}
