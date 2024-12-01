package com.example.springbootstudent.controller;

import com.example.springbootstudent.controller.request.BookRequest;
import com.example.springbootstudent.dto.AuthorDTO;
import com.example.springbootstudent.dto.BookDTO;
import com.example.springbootstudent.exception.NotFoundException;
import com.example.springbootstudent.model.Book;
import com.example.springbootstudent.service.inter.BookServiceInter;
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
@RequestMapping("/book")
@Slf4j
@Tag(name = "Book Controller", description = "Operations related to book management")
public class BookController {

    private final BookServiceInter bookServiceInter;

    public BookController(BookServiceInter bookServiceInter) {
        this.bookServiceInter = bookServiceInter;
    }

    @Operation(summary = "Save book", description = "Fill book information and save it!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully saved!"),
            @ApiResponse(responseCode = "500", description = "Can't save book!")
    })
    @PostMapping("/save/{categoryId}/{authorId}")
    public ResponseEntity<BookRequest> save(@RequestBody BookRequest bookRequest, @PathVariable Long categoryId, @PathVariable List<Long> authorId) {
       try{
           bookServiceInter.save(bookRequest,categoryId,authorId);
           log.info("Successfully created {}",bookRequest);
           return new ResponseEntity<>(bookRequest,HttpStatus.OK);
       }catch (Exception e){
           log.info("Error happened at trying creating book!");
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    @Operation(summary = "Get all books", description = "Get all books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The book was not found")
    })
    @GetMapping("/books")
    public ResponseEntity<List<BookDTO>> getAll() {
     List<BookDTO> books=bookServiceInter.getAll();
     if (books==null){
         return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
     }else {
         return new ResponseEntity<>(books, HttpStatus.OK);
     }
    }

    @Operation(summary = "Get all books", description = "Get all books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The book was not found")
    })
    @GetMapping("/findBooks")
    public ResponseEntity<List<BookDTO>> findAll() {
        List<BookDTO> books=bookServiceInter.findAll();

        if (books==null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(books, HttpStatus.OK);
        }
    }
    @Operation(summary = "Get all books by category", description = "Get all books by category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The books by category were not found")
    })
    @GetMapping("/getByCategory/{categoryName}")
    public ResponseEntity<List<BookDTO>> getBookByCategory(String categoryName) {
      try {
          List<BookDTO> bookDTOS=bookServiceInter.getBookByCategory(categoryName);
          return new ResponseEntity<>(bookDTOS, HttpStatus.OK);
      }catch (Exception e){
          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
    @Operation(summary = "Get a book by id", description = "Returns a book as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The book was not found")
    })
    @GetMapping("/getById/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        BookDTO bookDTO=bookServiceInter.getBookById(id);
        if (bookDTO==null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(bookDTO, HttpStatus.OK);
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
            List<AuthorDTO> authorDTOS=bookServiceInter.getAuthorByBookName(bookName);
            return new ResponseEntity<>(authorDTOS,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete a book by id", description = "Delete a book as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Can't delete book!")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable Long id) {
        try{
            bookServiceInter.deleteBookById(id);
            log.info("Successfully deleted!");
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            log.info("Error happened at trying deleting book!");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update book", description = "Fill book for change book's info and update it!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated!"),
            @ApiResponse(responseCode = "500", description = "Can't update book!")
    })
    @PutMapping("/update/{id}/{name}/{year}/{price}/{count}/{categoryId}/{authorsId}")
    public ResponseEntity<BookDTO> putById(Long id, String name,String year,double price,int count, Long categoryId, List<Long> authorsId) {
        BookDTO bookDTO=bookServiceInter.getBookById(id);
        try{
            bookServiceInter.putById(id,name,year,price,count,categoryId,authorsId);
            log.info("Successfully updated!");
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            log.info("Error happened at trying updating book!");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update book's name", description = "Fill book's name changing and update it!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated!"),
            @ApiResponse(responseCode = "500", description = "Can't update book!")
    })
    @PutMapping("/updateName/{id}/{name}")
    public ResponseEntity<BookDTO> updateName(@PathVariable Long id, @PathVariable String name) {
        BookDTO bookById=bookServiceInter.getBookById(id);
        try {
            bookServiceInter.updateName(id,name);
            log.info("Successfully updated book's name!");
            return new ResponseEntity<>(bookById,HttpStatus.OK);
        }catch (Exception e){
            log.error("Error happened at trying updating book's name!");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update book's category", description = "Fill book's category changing and update it!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated!"),
            @ApiResponse(responseCode = "500", description = "Can't update book's category!")
    })
    @PutMapping("updateCategory/{id}/{categoryId}")
    public ResponseEntity<BookDTO> updateBookCategory(@PathVariable Long id, @PathVariable Long categoryId) {
        BookDTO bookById=bookServiceInter.getBookById(id);
        try {
            bookServiceInter.updateBookCategory(id, categoryId);
            log.info("Successfully updated book's category!");
            return new ResponseEntity<>(bookById,HttpStatus.OK);
        }catch (Exception e){
            log.error("Error happened at trying updating book's category!");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update book's authors", description = "Fill book's authors changing and update it!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated!"),
            @ApiResponse(responseCode = "500", description = "Can't update book's authors!")
    })
    @PutMapping("/updateBookAuthor/{bookId}/{authorsId}")
    public ResponseEntity<BookDTO> updateBookAuthor(@PathVariable Long bookId, @PathVariable List<Long> authorsId) {
        BookDTO bookById=bookServiceInter.getBookById(bookId);

        try{
            bookServiceInter.updateBookAuthor(bookId,authorsId);
            log.info("Successfully updated book's authors!");
            return new ResponseEntity<>(bookById,HttpStatus.OK);
        }catch (Exception e){
            log.error("Error happened at trying updating book's authors!");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
