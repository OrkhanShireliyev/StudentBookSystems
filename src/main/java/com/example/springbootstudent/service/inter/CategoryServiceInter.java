package com.example.springbootstudent.service.inter;

import com.example.springbootstudent.controller.request.CategoryRequest;
import com.example.springbootstudent.dto.StudentDTO;
import com.example.springbootstudent.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryServiceInter {

    void save(CategoryRequest categoryRequest);

    List<CategoryRequest> getAll();

    CategoryRequest getCategoryById(Long id);

    void putById(Long id,String name);

    void deleteCategoryById(Long id);

}
