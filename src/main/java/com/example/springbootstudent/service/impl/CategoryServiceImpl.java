package com.example.springbootstudent.service.impl;

import com.example.springbootstudent.controller.request.CategoryRequest;
import com.example.springbootstudent.model.Category;
import com.example.springbootstudent.repository.jpa.CategoryRepositoryJpa;
import com.example.springbootstudent.repository.jpa.StudentRepositoryJpa;
import com.example.springbootstudent.service.inter.CategoryServiceInter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryServiceInter {

    private final CategoryRepositoryJpa categoryJpa;
    private final StudentRepositoryJpa studentRepositoryJPA;


    public CategoryServiceImpl(CategoryRepositoryJpa categoryJpa,
                               StudentRepositoryJpa studentRepositoryJPA) {
        this.categoryJpa = categoryJpa;
        this.studentRepositoryJPA = studentRepositoryJPA;
    }

    @Override
    public void save(CategoryRequest categoryRequest) {
        Category category=Category.builder()
                        .name(categoryRequest.getName())
                                .build();

        categoryJpa.save(category);
    }

    @Override
    public List<CategoryRequest> getAll() {
        List<Category> categories=categoryJpa.findAll();
        List<CategoryRequest> categoryRequests=new ArrayList<>();
        CategoryRequest categoryRequest=new CategoryRequest();

        for (Category category:categories){
            categoryRequest.setName(category.getName());
            categoryRequests.add(categoryRequest);
        }
        return categoryRequests;
    }

    @Override
    public CategoryRequest getCategoryById(Long id) {
        Category category=categoryJpa.findById(id).get();
        CategoryRequest categoryRequest=CategoryRequest.builder().name(category.getName()).build();

        return categoryRequest;
    }

    @Override
    public void putById(Long id, String name) {
        Category category=categoryJpa.findById(id).get();
        category.setName(name);
        categoryJpa.save(category);
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryJpa.deleteById(id);
    }
}
