package com.example.springbootstudent.service.impl;

import com.example.springbootstudent.controller.request.StudentRequest;
import com.example.springbootstudent.dto.StudentDTO;
import com.example.springbootstudent.exception.AlreadyExistException;
import com.example.springbootstudent.exception.NotFoundException;
import com.example.springbootstudent.model.Student;
import com.example.springbootstudent.repository.jpa.StudentRepositoryJpa;
import com.example.springbootstudent.service.inter.StudentServiceInter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceJpa implements StudentServiceInter {

    private final StudentRepositoryJpa studentRepositoryJPA;

    public StudentServiceJpa(StudentRepositoryJpa studentRepositoryJPA) {
        this.studentRepositoryJPA = studentRepositoryJPA;
    }

    @Override
    public void save(StudentRequest studentRequest) {
        List<Student> students=studentRepositoryJPA.findAll();

        boolean checkName=students.stream().anyMatch(student -> student.getName().equals(studentRequest.getName()));

        boolean checkSurname=students.stream().anyMatch(student -> student.getSurname().equals(studentRequest.getSurname()));

        boolean checkNameAndSurname=checkName&&checkSurname;

        if (checkNameAndSurname==true){
            throw new AlreadyExistException("Already exist student with name="+studentRequest.getName()+", surname="+studentRequest.getSurname());
        }

        Student student=Student.builder()
                        .name(studentRequest.getName())
                                .surname(studentRequest.getSurname())
                                        .age(studentRequest.getAge())
                                                .score(studentRequest.getScore())
                                                        .groupName(studentRequest.getGroupName())
                                                                .build();

      studentRepositoryJPA.save(student);
    }

    public void saveStudentWithImage(String name,String surname,int age,int score,String groupName, MultipartFile file){
        String uploadDirectory= System.getProperty("user.home") + "/Desktop/";

        if (file.isEmpty()) {
            throw new NotFoundException("İmage not selected!");
        }

        try {
            String imagePath = uploadDirectory + file.getOriginalFilename();
            Path path = Paths.get(imagePath);
            Files.write(path, file.getBytes());

            Student student = Student.builder()
                            .name(name)
                            .surname(surname)
                            .age(age)
                            .score(score)
                            .groupName(groupName)
                            .imagePath(imagePath)
                            .build();

            studentRepositoryJPA.save(student);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResponseEntity<InputStreamResource> downloadStudentImage(Long id) throws IOException {
        Student student = studentRepositoryJPA.findById(id).get();

        if (student==null){
           throw new NotFoundException("Not found student with id=" + id);
        }

        File file = new File(student.getImagePath());

        if (!file.exists()) {
            return ResponseEntity.badRequest().body(null);
        }

        String downloadDir = System.getProperty("user.home") + "/Desktop/download/";
        File downloadDirFile = new File(downloadDir);
        if (!downloadDirFile.exists()) {
            downloadDirFile.mkdirs(); // Qovluq yoxdursa, yaradın
        }

        // Yeni faylın adı
        File newFile = new File(downloadDir + file.getName());

        // Faylı yükləyin
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + newFile.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.length())
                .body(resource);
    }

    @Override
    public List<StudentDTO> getAll() {
        StudentDTO studentDTO;
        List<StudentDTO> studentDTOS=new ArrayList<>();
        List<Student> students=studentRepositoryJPA.findAll();

        if (students==null){
            throw new NotFoundException("Not found students!");
        }

        for (Student student:students){
            studentDTO=StudentDTO.builder()
                    .name(student.getName())
                    .surname(student.getSurname())
                    .age(student.getAge())
                    .score(student.getScore())
                    .groupName(student.getGroupName())
                    .imagePath(student.getImagePath())
                    .build();

            studentDTOS.add(studentDTO);
        }
        return studentDTOS;
    }

    @Override
    public StudentDTO getStudentById(Long id) {

        Student studentById=studentRepositoryJPA.findById(id).get();

        if (studentById==null){
            throw new NotFoundException("Not found student with id=" + id);
        }

        StudentDTO studentDTO=StudentDTO.builder()
                .name(studentById.getName())
                .surname(studentById.getSurname())
                .age(studentById.getAge())
                .score(studentById.getScore())
                .groupName(studentById.getGroupName())
                .build();

        return studentDTO;
    }

    @Override
    public void deleteStudentById(Long id) {
        Student studentById=studentRepositoryJPA.findById(id).get();

        if (studentById==null){
            throw new NotFoundException("Not found student with id=" + id);
        }

        studentRepositoryJPA.deleteById(id);
    }

    @Override
    public void putById(Long id, String name, String surname, int age, int score, String groupName) {
        Student studentById=studentRepositoryJPA.findById(id).get();

        if (studentById==null){
            throw new NotFoundException("Not found student with id=" + id);
        }
        studentById.setName(name);
        studentById.setSurname(surname);
        studentById.setAge(age);
        studentById.setScore(score);
        studentById.setGroupName(groupName);

        studentRepositoryJPA.save(studentById);
    }

    @Override
    public void updateName(Long id, String name) {
        Student studentById=studentRepositoryJPA.findById(id).get();

        if (studentById==null){
            throw new NotFoundException("Not found student with id=" + id);
        }
        studentById.setName(name);

        studentRepositoryJPA.save(studentById);
    }

    @Override
    public void updateSurname(Long id, String surname) {
        Student studentById=studentRepositoryJPA.findById(id).get();

        if (studentById==null){
            throw new NotFoundException("Not found student with id=" + id);
        }
        studentById.setSurname(surname);

        studentRepositoryJPA.save(studentById);
    }

    @Override
    public void updateAge(Long id, int age) {
        Student studentById=studentRepositoryJPA.findById(id).get();

        if (studentById==null){
            throw new NotFoundException("Not found student with id=" + id);
        }
        studentById.setAge(age);

        studentRepositoryJPA.save(studentById);
    }

    @Override
    public void updateGroupName(Long id, String groupName) {
        Student studentById=studentRepositoryJPA.findById(id).get();

        if (studentById==null){
            throw new NotFoundException("Not found student with id=" + id);
        }
        studentById.setGroupName(groupName);

        studentRepositoryJPA.save(studentById);
    }
}
