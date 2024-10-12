package com.example.springbootstudent.service.impl;

import com.example.springbootstudent.controller.request.StudentRequest;
import com.example.springbootstudent.dto.StudentDTO;
import com.example.springbootstudent.exception.AlreadyExistException;
import com.example.springbootstudent.exception.NotFoundException;
import com.example.springbootstudent.model.Student;
import com.example.springbootstudent.repository.inter.StudentRepositoryInter;
import com.example.springbootstudent.service.inter.StudentServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentServiceInter {

    private final StudentRepositoryInter studentRepositoryInter;

    @Autowired
    public StudentServiceImpl(StudentRepositoryInter studentRepositoryInter) {
        this.studentRepositoryInter = studentRepositoryInter;
    }

    @Override
    public void save(StudentRequest studentRequest) {
        List<Student> students=studentRepositoryInter.getAll();
        boolean checkName=students.stream().allMatch(student -> student.getName().equals(studentRequest.getName()));

        boolean checkSurname=students.stream().allMatch(student -> student.getSurname().equals(studentRequest.getSurname()));

        boolean checkNameAndSurname=checkName&&checkSurname;

        if (checkNameAndSurname=true){
            throw new AlreadyExistException("Already exist student with name="+studentRequest.getName()+", surname="+studentRequest.getSurname());
        }

        Student student=new Student(
                studentRequest.getName(),
                studentRequest.getSurname(),
                studentRequest.getAge(),
                studentRequest.getScore(),
                studentRequest.getGroupName());

//        student.setName(studentRequest.getName());
//        student.setSurname(studentRequest.getSurname());
//        student.setAge(studentRequest.getAge());
//        student.setScore(studentRequest.getScore());
//        student.setGroupName(studentRequest.getGroupName());

        studentRepositoryInter.save(student);
    }

    @Override
    public List<StudentDTO> getAll() {
       List<Student> students=studentRepositoryInter.getAll();

       List<StudentDTO> studentDTOS=new ArrayList<>();

       for (Student student:students){
           StudentDTO studentDTO=new StudentDTO();
           studentDTO.setName(student.getName());
           studentDTO.setSurname(student.getSurname());
           studentDTO.setAge(student.getAge());
           studentDTO.setScore(student.getScore());
           studentDTO.setGroupName(student.getGroupName());

           studentDTOS.add(studentDTO);
       }
        return studentDTOS;
    }

    @Override
    public StudentDTO getStudentById(Long id) {

            Student student = studentRepositoryInter.getStudentById(id);
            if (student==null) {
                throw new NotFoundException("Not found student with id=" + id);
            }

            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setName(student.getName());
            studentDTO.setSurname(student.getSurname());
            studentDTO.setAge(student.getAge());
            studentDTO.setScore(student.getScore());
            studentDTO.setGroupName(student.getGroupName());

            return studentDTO;
    }

    @Override
    public void deleteStudentById(Long id) {
        Student student=studentRepositoryInter.getStudentById(id);
        if (student==null){
            new NotFoundException("Not found student with id="+id);
        }
        studentRepositoryInter.deleteStudentById(id);
    }

    @Override
    public void putById(Long id, String name, String surname, int age, int score, String groupName) {
        Student student=studentRepositoryInter.getStudentById(id);
        if (student==null){
            new NotFoundException("Not found student with id="+id);
        }
        studentRepositoryInter.putById(id,name,surname,age,score,groupName);
    }

    @Override
    public void updateName(Long id, String name) {
        Student student=studentRepositoryInter.getStudentById(id);
        if (student==null){
            new NotFoundException("Not found student with id="+id);
        }
        studentRepositoryInter.updateName(id,name);
    }

    @Override
    public void updateSurname(Long id, String surname) {
        Student student=studentRepositoryInter.getStudentById(id);
        if (student==null){
            new NotFoundException("Not found student with id="+id);
        }
        studentRepositoryInter.updateSurname(id,surname);
    }

    @Override
    public void updateAge(Long id, int age) {
        Student student=studentRepositoryInter.getStudentById(id);
        if (student==null){
            new NotFoundException("Not found student with id="+id);
        }
        studentRepositoryInter.updateAge(id,age);
    }

    @Override
    public void updateGroupName(Long id, String groupName){
        Student student=studentRepositoryInter.getStudentById(id);
        if (student==null){
            new NotFoundException("Not found student with id="+id);
        }
        studentRepositoryInter.updateGroupName(id,groupName);
    }
}
