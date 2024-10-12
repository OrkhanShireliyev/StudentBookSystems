package com.example.springbootstudent.service.inter;

import com.example.springbootstudent.controller.request.StudentRequest;
import com.example.springbootstudent.dto.StudentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentServiceInter {

    void save(StudentRequest studentRequest);

    List<StudentDTO> getAll();

    StudentDTO getStudentById(Long id);

    void deleteStudentById(Long id);

    void putById(Long id,String name,String surname,int age,int score,String groupName);

    void updateName(Long id,String name);

    void updateSurname(Long id,String surname);

    void updateAge(Long id,int age);

    void updateGroupName(Long id, String groupName);
}
