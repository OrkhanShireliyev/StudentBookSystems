package com.example.springbootstudent.controller;

import com.example.springbootstudent.controller.request.AuthorRequest;
import com.example.springbootstudent.dto.AuthorDTO;
import com.example.springbootstudent.exception.NotFoundException;
import com.example.springbootstudent.service.inter.AuthorServiceInter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
@Tag(name = "Author Controller", description = "Operations related to author management")
@Slf4j
public class AuthorController {
    private final AuthorServiceInter authorServiceInter;
    public AuthorController(AuthorServiceInter authorServiceInter) {
        this.authorServiceInter = authorServiceInter;
    }

    @Operation(summary = "Save authors!", description = "Fill author information and save it!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully saved!"),
            @ApiResponse(responseCode = "500", description = "Can't save author!")
    })
    @PostMapping("/save/{bookId}")
    public void save(@RequestBody AuthorRequest authorRequest,@PathVariable List<Long> bookId) {
        authorServiceInter.save(authorRequest,bookId);
    }

    @Operation(summary = "Save authors!", description = "Fill author information and save it!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully saved!"),
            @ApiResponse(responseCode = "500", description = "Can't save author!")
    })
    @PostMapping("/saveWithoutBook")
    public void saveWithoutBook(@RequestBody AuthorRequest authorRequest) {
        authorServiceInter.saveWithoutBook(authorRequest);
    }

    @Operation(summary = "Get all authors", description = "Get all authors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The authors were not found")
    })
    @GetMapping("/authors")
    public ResponseEntity<List<AuthorDTO>> getAll() {
        try {
            List<AuthorDTO> authorDTOS=authorServiceInter.getAll();
            return new ResponseEntity<>(authorDTOS,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get a book by id", description = "Returns a book as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The author was not found")
    })
    @GetMapping("/getById/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
        try {
            AuthorDTO authorDTO=authorServiceInter.getAuthorById(id);
            return new ResponseEntity<>(authorDTO,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get a authors by books name", description = "Returns a author as per the book name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The author was not found")
    })
    @GetMapping("/getByBookName/{bookName}")
    public ResponseEntity<List<AuthorDTO>> getAuthorByBookName(@PathVariable String bookName) {
        try{
            List<AuthorDTO> authorDTOS=authorServiceInter.getAuthorByBookName(bookName);
            return new ResponseEntity<>(authorDTOS,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete a book by id", description = "Delete a book as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Can't delete author!")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAuthorById(@PathVariable Long id) {
        try{
            authorServiceInter.deleteAuthorById(id);
            return new ResponseEntity<>("Successfully deleted!",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error happened at trying deleting author!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update author!", description = "Fill author changing and update it!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated!"),
            @ApiResponse(responseCode = "500", description = "Can't update author!")
    })
    @PutMapping("/update/{id}/{name}/{surname}/{bookId}")
    public ResponseEntity<AuthorDTO> putById(@PathVariable Long id,
                                             @PathVariable String name,
                                             @PathVariable String surname,
                                             @PathVariable List<Long> bookId) {
        AuthorDTO authorById=authorServiceInter.getAuthorById(id);
        if (authorById==null){
            throw new NotFoundException("Not found author by id="+id);
        }
        try{
            authorServiceInter.putById(id, name, surname, bookId);
            return new ResponseEntity<>(authorById,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update author's name", description = "Fill author's name changing and update it!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated!"),
            @ApiResponse(responseCode = "500", description = "Can't update author's name!")
    })
    @PutMapping("/update/{id}/{name}")
    public ResponseEntity<AuthorDTO> updateName(@PathVariable Long id,@PathVariable String name) {
        AuthorDTO authorById=authorServiceInter.getAuthorById(id);
        if (authorById==null){
            throw new NotFoundException("Not found author by id="+id);
        }
        try{
            authorServiceInter.updateName(id, name);
            return new ResponseEntity<>(authorById,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update author's surname", description = "Fill author's surname changing and update it!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated!"),
            @ApiResponse(responseCode = "500", description = "Can't update author's surname!")
    })
    @PutMapping("/update/{id}/{surname}")
    public ResponseEntity<AuthorDTO> updateSurname(@PathVariable Long id,@PathVariable String surname) {
        AuthorDTO authorById=authorServiceInter.getAuthorById(id);
        if (authorById==null){
            throw new NotFoundException("Not found author by id="+id);
        }
        try{
            authorServiceInter.updateSurname(id, surname);
            return new ResponseEntity<>(authorById,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update author's books", description = "Fill author's books changing and update it!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated!"),
            @ApiResponse(responseCode = "500", description = "Can't update author's books!")
    })
    @PutMapping("/update/{id}/{bookId}")
    public ResponseEntity<AuthorDTO> updateAuthorBook(@PathVariable Long id,@PathVariable List<Long> bookId) {
        AuthorDTO authorById=authorServiceInter.getAuthorById(id);
        if (authorById==null){
            throw new NotFoundException("Not found author by id="+id);
        }
        try{
            authorServiceInter.updateAuthorBook(id,bookId);
            return new ResponseEntity<>(authorById,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
