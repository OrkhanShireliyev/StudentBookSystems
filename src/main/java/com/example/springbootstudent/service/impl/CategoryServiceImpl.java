package com.example.springbootstudent.service.impl;

import com.example.springbootstudent.controller.request.CategoryRequest;
import com.example.springbootstudent.dto.CategoryDTO;
import com.example.springbootstudent.exception.AlreadyExistException;
import com.example.springbootstudent.exception.NotFoundException;
import com.example.springbootstudent.model.Category;
import com.example.springbootstudent.repository.jpa.BookRepositoryJpa;
import com.example.springbootstudent.repository.jpa.CategoryRepositoryJpa;
import com.example.springbootstudent.repository.jpa.StudentRepositoryJpa;
import com.example.springbootstudent.service.inter.CategoryServiceInter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryServiceInter {
    private final BookRepositoryJpa bookRepositoryJpa;

    private final CategoryRepositoryJpa categoryJpa;
    private final StudentRepositoryJpa studentRepositoryJPA;


    public CategoryServiceImpl(CategoryRepositoryJpa categoryJpa,
                               StudentRepositoryJpa studentRepositoryJPA,
                               BookRepositoryJpa bookRepositoryJpa) {
        this.categoryJpa = categoryJpa;
        this.studentRepositoryJPA = studentRepositoryJPA;
        this.bookRepositoryJpa = bookRepositoryJpa;
    }

    @Override
    public void save(CategoryRequest categoryRequest) {
        boolean isExist=categoryJpa.findAll().stream().anyMatch(category -> category.getName().equals(categoryRequest.getName()));

        if (isExist==true){
            throw new AlreadyExistException("Already exist as this category with name="+categoryRequest.getName());
        }

        Category category=Category.builder()
                        .name(categoryRequest.getName())
                                .build();

        categoryJpa.save(category);
    }

    @Override
    public List<CategoryDTO> getAll() {
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        CategoryDTO categoryDTO = new CategoryDTO();
        List<Category> categories=categoryJpa.findAll();

        if (categories==null){
            throw new NotFoundException("Not found categories!");
        }

        try {
            for (Category category : categories) {
                categoryDTO.setName(category.getName());
//                List<Book> books=bookRepositoryJpa.findAll().stream().filter(book -> book.getCategory().getName().equals(category.getName())).collect(Collectors.toList());
//                categoryDTO.setBooks(books);
                categoryDTOS.add(categoryDTO);
            }
            log.info("Successfully retrieved{}",categoryDTOS);
            return categoryDTOS;
        }catch (Exception e){
            log.error("Error happened at trying retrieving categories!");
        }
        return categoryDTOS;
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        CategoryDTO categoryDTO = new CategoryDTO();
        Category category=categoryJpa.findById(id).get();
        if (category==null){
            throw new NotFoundException("Not found category with id="+id);
        }
        try {
         categoryDTO=CategoryDTO.builder().name(category.getName()).build();

        log.info("Successfully retrieved{}",categoryDTO);

        return categoryDTO;
    }catch (Exception e){
        log.error("Error happened at trying retrieving category with id="+id);
    }
        return categoryDTO;
    }

    @Override
    public void putById(Long id, String name) {
        Category category=categoryJpa.findById(id).get();
        if (category==null){
            throw new NotFoundException("Not found category with id="+id);
        }

        try {
            category.setName(name);
            categoryJpa.save(category);
            log.info("Successfully updated{}",category);
        }catch (Exception e){
            log.error("Error happened at trying updating category with id="+id);
        }
    }

    @Override
    public void deleteCategoryById(Long id) {
        Category category=categoryJpa.findById(id).get();
        if (category==null){
            throw new NotFoundException("Not found category with id="+id);
        }
        try {
            categoryJpa.deleteById(id);
        }catch (Exception e){
            log.error("Error happened at trying deleting category with id="+id);
        }

    }
}
