package com.example.springbootstudent.service.impl;

import com.example.springbootstudent.controller.request.BookRequest;
import com.example.springbootstudent.dto.AuthorDTO;
import com.example.springbootstudent.dto.BookDTO;
import com.example.springbootstudent.exception.AlreadyExistException;
import com.example.springbootstudent.exception.NotFoundException;
import com.example.springbootstudent.model.Author;
import com.example.springbootstudent.model.Book;
import com.example.springbootstudent.model.Category;
import com.example.springbootstudent.repository.jpa.AuthorRepositoryJpa;
import com.example.springbootstudent.repository.jpa.BookRepositoryJpa;
import com.example.springbootstudent.repository.jpa.CategoryRepositoryJpa;
import com.example.springbootstudent.service.inter.BookServiceInter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BookServiceImpl implements BookServiceInter {
    private final BookRepositoryJpa bookRepositoryJpa;
    private final CategoryRepositoryJpa categoryRepositoryJpa;

    private final AuthorRepositoryJpa authorRepositoryJpa;

    public BookServiceImpl(BookRepositoryJpa bookRepositoryJpa,
                           CategoryRepositoryJpa categoryRepositoryJpa, AuthorRepositoryJpa authorRepositoryJpa) {
        this.bookRepositoryJpa = bookRepositoryJpa;
        this.categoryRepositoryJpa = categoryRepositoryJpa;
        this.authorRepositoryJpa = authorRepositoryJpa;
    }

    @Override
    public void save(BookRequest bookRequest, Long categoryId, List<Long> authorId) {
        Category category=categoryRepositoryJpa.findById(categoryId).get();
        if (category==null){
            throw new NotFoundException("Not found category with id="+categoryId);
        }

        List<Author> authorsById=authorRepositoryJpa.findAllById(authorId);
        authorsById.stream().forEach(System.out::println);

        if (authorsById==null){
            throw new NotFoundException("Not found authors!");
        }
        boolean isExistBook = false;
        try {
            Book book = new Book();
            try{
                Book getBookByName=bookRepositoryJpa.findBookByName(bookRequest.getName());
                boolean bookIsExist=getBookByName.getName().equals(bookRequest.getName());
                boolean bookIsCategory=bookRepositoryJpa.findBooksByCategoryName(category.getName()).stream().findFirst().isPresent();
                isExistBook=bookIsExist&&bookIsCategory;
                if (isExistBook==true){
                    throw new AlreadyExistException("TRY: Already exist as this book with name= "+bookRequest.getName()+", category= "+category.getName());
                }
            }catch (AlreadyExistException alreadyExistException){
                if (isExistBook==true){
                    throw new AlreadyExistException("CATCH: Already exist as this book with name= "+bookRequest.getName()+", category= "+category.getName());
                }
            }catch (NullPointerException e){

            }
            book.setName(bookRequest.getName());
            book.setYear(bookRequest.getYear());
            book.setPrice(bookRequest.getPrice());
            book.setCount(bookRequest.getCount());
            book.setCategory(category);
            book.setAuthors(authorsById);
            bookRepositoryJpa.save(book);
            log.info("Successfully created {}", book);
        }catch (Exception e){
            e.printStackTrace();
            log.info("Error happened at trying creating book!");
        }
    }

    @Override
    public List<BookDTO> getAll() {
        List<Book> books=bookRepositoryJpa.findAllBooksWithAuthorsAndCategory();

        List<BookDTO> bookDTOS = new ArrayList<>();
        BookDTO bookDTO;

        if (books==null){
            throw new NotFoundException("Not found books!");
        }

       try {
           for (Book book : books) {
               bookDTO = BookDTO.builder()
                       .name(book.getName())
                       .year(book.getYear())
                       .price(book.getPrice())
                       .count(book.getCount())
                       .category(book.getCategory())
                       .authors(book.getAuthors())
                       .build();

               bookDTOS.add(bookDTO);
               log.info("Successfully retrieved{}",bookDTOS);
           }
       }catch (Exception e){
           log.error("Error happened at trying retrieving books!");
       }
        return bookDTOS;
    }

    @Override
    public List<BookDTO> findAll() {
        List<Book> books=bookRepositoryJpa.findAll();

        if (books==null){
            throw new NotFoundException("Not found books!");
        }

        List<BookDTO> bookDTOS = new ArrayList<>();
        BookDTO bookDTO;

        try {
            for (Book book : books) {
                bookDTO = BookDTO.builder()
                        .name(book.getName())
                        .year(book.getYear())
                        .price(book.getPrice())
                        .count(book.getCount())
                        .category(book.getCategory())
                        .authors(book.getAuthors())
                        .build();

                bookDTOS.add(bookDTO);
            }
            log.info("Successfully retrieved{}",bookDTOS);
        }catch (Exception e){
            log.error("Error happened at trying retrieving books!");
        }
        return bookDTOS;
    }

    @Override
    public List<BookDTO> getBookByCategory(String categoryName) {
        List<Book> books=bookRepositoryJpa.findBooksByCategoryName(categoryName);
        if (books==null){
            throw new NotFoundException("Not found book with category name="+categoryName);
        }

        List<BookDTO> bookDTOS = new ArrayList<>();
        BookDTO bookDTO = new BookDTO();

        try {
            for (Book book : books) {
                bookDTO.setName(book.getName());
                bookDTO.setYear(book.getYear());
                bookDTO.setPrice(book.getPrice());
                bookDTO.setCount(book.getCount());
                bookDTO.setCategory(book.getCategory());
                bookDTO.setAuthors(book.getAuthors());
                bookDTOS.add(bookDTO);
                log.info("Successfully retrieved{}",bookDTOS);
            }
        }catch (Exception e){
            log.error("Error happened at trying retrieving books by category name="+categoryName);
        }
        return bookDTOS;
    }

    @Override
    public BookDTO getBookById(Long id) {
        Book bookById=bookRepositoryJpa.findById(id).get();
        if (bookById==null){
            throw new NotFoundException("Not found book with id="+id);
        }

        BookDTO bookDTO = new BookDTO();

        try{
            bookDTO=BookDTO.builder()
                    .name(bookById.getName())
                    .year(bookById.getYear())
                    .price(bookById.getPrice())
                    .count(bookById.getCount())
                    .category(bookById.getCategory())
                    .authors(bookById.getAuthors())
                    .build();

            log.info("Successfully retrieved{}",bookDTO);
        }catch (Exception e){
            log.error("Not found book with id="+id);
        }

        return bookDTO;
    }

    @Override
    public List<AuthorDTO> getAuthorByBookName(String bookName) {
        Book bookByName=bookRepositoryJpa.findBookByName(bookName);
        System.out.println(bookByName.toString());

        List<Author> authors=bookByName.getAuthors();

        AuthorDTO authorDTO=new AuthorDTO();
        List<AuthorDTO> authorDTOS=new ArrayList<>();

        try{
            for (Author author:authors) {
                authorDTO.setName(author.getName());
                authorDTO.setSurname(author.getSurname());
                authorDTO.setBooks(author.getBooks());

                authorDTOS.add(authorDTO);
                log.info("Successfully retrieved {}",authorDTOS);
            }
        }catch (Exception e){
            log.error("Error happened at trying retrieving author!");
        }
        return authorDTOS;

    }

    @Override
    public void deleteBookById(Long id) {
        Book bookById=bookRepositoryJpa.findById(id).get();
        if (bookById==null){
            throw new NotFoundException("Not found book with id="+id);
        }
        bookRepositoryJpa.deleteById(id);
        log.info("Successfully deleted {}");
    }

    @Override
    public void putById(Long id, String name,String year,double price,int count, Long categoryId, List<Long> authorsId) {
        Book bookById=bookRepositoryJpa.findById(id).get();
        if (bookById==null){
            throw new NotFoundException("Not found book with id="+id);
        }

        Category category=categoryRepositoryJpa.findById(categoryId).get();
        if (category==null){
            throw new NotFoundException("Not found category with id="+categoryId);
        }

        List<Author> authors=authorRepositoryJpa.findAllById(authorsId);
        if (authors==null){
            throw new NotFoundException("Not found authors with id="+authorsId);
        }

        try{
            bookById=Book.builder()
                    .name(name)
                    .year(year)
                    .price(price)
                    .count(count)
                    .category(category)
                    .authors(authors)
                    .build();

            bookRepositoryJpa.save(bookById);
            log.info("Successfully updated book{}",bookById);
        }catch (Exception e){
            log.error("Error happened at trying updated book{}",bookById);
        }
    }

    @Override
    public void updateName(Long id, String name) {
        Book bookById=bookRepositoryJpa.findById(id).get();
        if (bookById==null){
            throw new NotFoundException("Not found book with id="+id);
        }
        bookById.setName(name);
        bookRepositoryJpa.save(bookById);
        log.info("Successfully updated book's name{}",bookById);
    }

    @Override
    public void updateBookCategory(Long id, Long categoryId) {
        Book bookById=bookRepositoryJpa.findById(id).get();
        if (bookById==null){
            throw new NotFoundException("Not found book with id="+id);
        }
        Category categoryById= categoryRepositoryJpa.findById(categoryId).get();
        if (categoryById==null){
            throw new NotFoundException("Not found category with id="+categoryId);
        }
        bookById.setCategory(categoryById);

        bookRepositoryJpa.save(bookById);
        log.info("Successfully updated book's category{}",bookById);
    }

    @Override
    public void updateBookAuthor(Long id, List<Long> authorsId) {
        Book bookById=bookRepositoryJpa.findById(id).get();
        if (bookById==null){
            throw new NotFoundException("Not found book with id="+id);
        }
        List<Author> authors=authorRepositoryJpa.findAllById(authorsId);
        if (authors==null){
            throw new NotFoundException("Not found authors with id="+authorsId);
        }

        bookById.setAuthors(authors);

        bookRepositoryJpa.save(bookById);
        log.info("Successfully updated book's authors{}",bookById);
    }
}
