package com.example.springbootstudent.service.impl;

import com.example.springbootstudent.controller.request.StudentRequest;
import com.example.springbootstudent.dto.StudentDTO;
import com.example.springbootstudent.exception.AlreadyExistException;
import com.example.springbootstudent.exception.NotFoundException;
import com.example.springbootstudent.model.Student;
import com.example.springbootstudent.repository.jpa.StudentRepositoryJpa;
import com.example.springbootstudent.service.inter.StudentServiceInter;
import com.example.springbootstudent.service.redis.StudentRedisService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class StudentServiceJpa implements StudentServiceInter {

    private final StudentRepositoryJpa studentRepositoryJPA;

    private final StudentRedisService studentRedisService;

    public StudentServiceJpa(StudentRepositoryJpa studentRepositoryJPA, StudentRedisService studentRedisService) {
        this.studentRepositoryJPA = studentRepositoryJPA;
        this.studentRedisService = studentRedisService;
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
                                                            .imagePath(studentRequest.getImagePath())
                                                                .build();

        Student saveDb=studentRepositoryJPA.save(student);
        studentRedisService.save(saveDb);
        log.info("Successfully saved{}",saveDb);
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
            studentRedisService.save(student);

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
        List<Student> returnStudent;
        List<Student> students;

        List<StudentDTO> studentDTOS=new ArrayList<>();
        List<Student> studentsRedis=studentRedisService.getAll();
        if (studentsRedis==null){
            throw new NotFoundException("Not found student in redis!");
        }

        if (studentsRedis!=null){
            returnStudent=studentsRedis;
            log.info("Students retrieved from redis!");
        }else{
            students=studentRepositoryJPA.findAll();
            returnStudent=students;
            if (students==null){
                throw new NotFoundException("Not found student!");
            }
            log.info("Students retrieved from db!");
        }

        for (Student student:returnStudent){
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
        log.info("Successfully retrieved{}",studentsRedis);
        return studentDTOS;
    }

    @Override
    public StudentDTO getStudentById(Long id) {

        Student student;

        Student studentById;

        Student studentByRedis=studentRedisService.getStudent(id);

        if (studentByRedis!=null){
            student=studentByRedis;
            log.info("Student by id="+id+" retrieved from redis!");
        }else {
            studentById=studentRepositoryJPA.findById(id).get();
            student=studentById;
            log.info("Student by id="+id+" retrieved from db!");
            if (studentById==null) {
                throw new NotFoundException("Not found student with id=" + id);
            }
        }

        StudentDTO studentDTO=StudentDTO.builder()
                .name(student.getName())
                .surname(student.getSurname())
                .age(student.getAge())
                .score(student.getScore())
                .groupName(student.getGroupName())
                .build();
        log.info("Successfully retrieved{}",studentDTO);
        return studentDTO;
    }

    @Override
    public void deleteStudentById(Long id) {

        Student studentByRedis=studentRedisService.getStudent(id);

        if (studentByRedis!=null){
            studentRedisService.delete(id);
            log.info("Deleted student by id="+id+" retrieved from redis!");
        }

        Student studentById=studentRepositoryJPA.findById(id).get();

        if (studentById!=null){
            studentRepositoryJPA.deleteById(id);
            log.info("Deleted student by id="+id+" retrieved from db!");
            if (studentById==null) {
                throw new NotFoundException("Not found student with id=" + id);
            }
        }
    }

    @Override
    public void putById(Long id, String name, String surname, int age, int score, String groupName) {
        Student student = new Student();

        Student studentByRedis=studentRedisService.getStudent(id);
        if (studentByRedis==null){
            throw new NotFoundException("Not found student at redis with id=" + id);
        }

        if (studentByRedis!=null){
            student=studentByRedis;
            log.info("Student by id="+id+" retrieved from redis!");
        }

        Student studentById=studentRepositoryJPA.findById(id).get();
        if (studentById==null){
            throw new NotFoundException("Not found student at db with id=" + id);
        }

        if (studentById!=null){
            student=studentById;
            log.info("Student by id="+id+" retrieved from db!");
            if (studentById==null) {
                throw new NotFoundException("Not found student with id=" + id);
            }
        }

        if (student==null){
            throw new NotFoundException("Not found student with id=" + id);
        }

        student.setName(name);
        student.setSurname(surname);
        student.setAge(age);
        student.setScore(score);
        student.setGroupName(groupName);

        studentRepositoryJPA.save(student);
        studentRedisService.update(student);
        log.info("Successfully updated{}",student);
    }

    @Override
    public void updateName(Long id, String name) {
        Student student = new Student();

        Student studentByRedis=studentRedisService.getStudent(id);

        if (studentByRedis!=null){
            student=studentByRedis;
            log.info("Student by id="+id+" retrieved from redis!");
        }

        Student studentById=studentRepositoryJPA.findById(id).get();

        if (studentById!=null){
            student=studentById;
            log.info("Student by id="+id+" retrieved from db!");
            if (studentById==null) {
                throw new NotFoundException("Not found student with id=" + id);
            }
        }

        if (student==null){
            throw new NotFoundException("Not found student with id=" + id);
        }
        student.setName(name);

        studentRepositoryJPA.save(student);
        studentRedisService.update(student);
        log.info("Successfully updated name{}",student);
    }

    @Override
    public void updateSurname(Long id, String surname) {
        Student student = new Student();

        Student studentByRedis=studentRedisService.getStudent(id);

        if (studentByRedis!=null){
            student=studentByRedis;
            log.info("Student by id="+id+" retrieved from redis!");
        }

        Student studentById=studentRepositoryJPA.findById(id).get();

        if (studentById!=null){

                studentById=studentRepositoryJPA.findById(id).get();
                student=studentById;
                log.info("Student by id="+id+" retrieved from db!");
                if (studentById==null) {
                    throw new NotFoundException("Not found student with id=" + id);
                }
            }


        if (student==null){
            throw new NotFoundException("Not found student with id=" + id);
        }

        student.setSurname(surname);

        studentRepositoryJPA.save(student);
        studentRedisService.update(student);
        log.info("Successfully updated surname{}",student);
    }

    @Override
    public void updateAge(Long id, int age) {
        Student student = new Student();

        Student studentByRedis=studentRedisService.getStudent(id);

        if (studentByRedis!=null){
            student=studentByRedis;
            log.info("Student by id="+id+" retrieved from redis!");
        }

        Student studentById=studentRepositoryJPA.findById(id).get();

        if (studentById!=null){
            studentById=studentRepositoryJPA.findById(id).get();
            student=studentById;
            log.info("Student by id="+id+" retrieved from db!");
            if (studentById==null) {
                throw new NotFoundException("Not found student with id=" + id);
            }
        }

        if (student==null){
            throw new NotFoundException("Not found student with id=" + id);
        }

        student.setAge(age);

        studentRepositoryJPA.save(student);
        studentRedisService.update(student);
        log.info("Successfully updated age{}",student);
    }

    @Override
    public void updateGroupName(Long id, String groupName) {
        Student student = new Student();

        Student studentByRedis=studentRedisService.getStudent(id);

        if (studentByRedis!=null){
            student=studentByRedis;
            log.info("Student by id="+id+" retrieved from redis!");
        }

        Student studentById=studentRepositoryJPA.findById(id).get();

        if (studentById!=null){
            studentById=studentRepositoryJPA.findById(id).get();
            student=studentById;
            log.info("Student by id="+id+" retrieved from db!");
            if (studentById==null) {
                throw new NotFoundException("Not found student with id=" + id);
            }
        }

        if (student==null){
            throw new NotFoundException("Not found student with id=" + id);
        }

        student.setGroupName(groupName);

        studentRepositoryJPA.save(student);
        studentRedisService.update(student);
        log.info("Successfully updated group name{}",student);
    }
}
