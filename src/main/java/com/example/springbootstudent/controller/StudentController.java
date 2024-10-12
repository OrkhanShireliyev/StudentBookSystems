package com.example.springbootstudent.controller;

import com.example.springbootstudent.controller.request.StudentRequest;
import com.example.springbootstudent.dto.StudentDTO;
import com.example.springbootstudent.service.impl.StudentServiceJpa;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@Tag(name = "Student Controller", description = "Operations related to student management")
public class StudentController {
 private final StudentServiceJpa studentServiceInter;

 @Autowired
 public StudentController(StudentServiceJpa studentServiceInter) {
  this.studentServiceInter = studentServiceInter;
 }

 @Operation(summary = "Save student", description = "Fill student information and save it!")
 @ApiResponses(value = {
         @ApiResponse(responseCode = "200", description = "Successfully saved!"),
         @ApiResponse(responseCode = "500", description = "Can't save student!")
 })
 @PostMapping("/save")
 public void save(@RequestBody StudentRequest studentRequest) {
  studentServiceInter.save(studentRequest);
 }

 @Operation(summary = "Save student with image", description = "Fill student information and save it!")
 @ApiResponses(value = {
         @ApiResponse(responseCode = "200", description = "Successfully saved!"),
         @ApiResponse(responseCode = "500", description = "Can't save student!")
 })
 @PostMapping(value = "/saveWithImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
 public void saveStudentWithImage(@RequestParam @Size(min = 3,max = 20) String name,
                                  @RequestParam @Size(min = 3,max = 20) String surname,
                                  @RequestParam @PositiveOrZero(message = "Age must be equal positive or zero!") int age,
                                  @RequestParam @PositiveOrZero(message = "Score must be equal positive or zero!")int score,
                                  @RequestParam String groupName,
                                  @RequestParam MultipartFile file) {
  studentServiceInter.saveStudentWithImage(name, surname, age, score, groupName, file);
 }

  @Operation(summary = "Download student's image", description = "Get student's image")
  @ApiResponses(value = {
         @ApiResponse(responseCode = "200", description = "Successfully retieved!"),
         @ApiResponse(responseCode = "500", description = "Can't save retieved!")
  })
  @PostMapping( "/downloadStudentImage/{id}")
  public ResponseEntity<InputStreamResource> downloadStudentImage(@PathVariable Long id) throws IOException {
    return studentServiceInter.downloadStudentImage(id);
 }


    @Operation(summary = "Get all students", description = "Get all students")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The student was not found")
    })
    @GetMapping("/students")
    public ResponseEntity<List<StudentDTO>> getAll(){
       List<StudentDTO> studentDTOS=studentServiceInter.getAll();
       return new ResponseEntity<>(studentDTOS, HttpStatus.OK);
    }

    @Operation(summary = "Get a student by id", description = "Returns a student as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The student was not found")
    })
    @GetMapping("/getById/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id){
       StudentDTO studentDTO=studentServiceInter.getStudentById(id);
       return new ResponseEntity<>(studentDTO,HttpStatus.OK);
    }

    @Operation(summary = "Delete a student by id", description = "Delete a student as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Can't delete student!")
    })
    @DeleteMapping("/delete/{id}")
    public void deleteStudentById(@PathVariable Long id){
       studentServiceInter.deleteStudentById(id);
    }

    @Operation(summary = "Update student", description = "Fill student for change student's info and update it!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated!"),
            @ApiResponse(responseCode = "500", description = "Can't update student!")
    })
    @PutMapping("/updateAll/{id}/{name}/{surname}/{age}/{score}/{groupName}")
    public void putById(
                        @PathVariable Long id,
                        @PathVariable String name,
                        @PathVariable String surname,
                        @PathVariable int age,
                        @PathVariable int score,
                        @PathVariable String groupName){

       studentServiceInter.putById(id,name,surname,age,score,groupName);
    }

    @Operation(summary = "Update student's name", description = "Fill student's name changing and update it!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated!"),
            @ApiResponse(responseCode = "500", description = "Can't update student!")
    })
    @PutMapping("/updatename")
    public void updateName(@RequestParam Long id,@RequestParam String name){
       studentServiceInter.updateName(id,name);
    }

    @Operation(summary = "Update student's surname", description = "Fill student's surname changing and update it!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated!"),
            @ApiResponse(responseCode = "500", description = "Can't update student!")
    })
    @PutMapping("/updatesurname")
    public void updateSurname(@RequestParam Long id,@RequestParam String surname){
       studentServiceInter.updateSurname(id,surname);
    }

    @Operation(summary = "Update student's age", description = "Fill student's age changing and update it!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated!"),
            @ApiResponse(responseCode = "500", description = "Can't update student!")
    })
    @PutMapping("/updateage")
    public void updateAge(@RequestParam Long id,@RequestParam int age){
       studentServiceInter.updateAge(id,age);
    }

    @Operation(summary = "Update student's group name", description = "Fill student's group name changing and update it!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated!"),
            @ApiResponse(responseCode = "500", description = "Can't update student!")
    })
    @PutMapping("/updategroupname")
    public void updateGroupName(@RequestParam Long id,@RequestParam String groupName){
       studentServiceInter.updateGroupName(id,groupName);
    }
}
