package com.example.springbootstudent.controller;

import com.example.springbootstudent.controller.request.CategoryRequest;
import com.example.springbootstudent.service.inter.CategoryServiceInter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryServiceInter categoryServiceInter;

    public CategoryController(CategoryServiceInter categoryServiceInter) {
        this.categoryServiceInter = categoryServiceInter;
    }

    @PostMapping("/save")
    public void save(@RequestBody CategoryRequest categoryRequest){
        categoryServiceInter.save(categoryRequest);
    };

    @GetMapping("/categories")
    public List<CategoryRequest> getAll(){
       return categoryServiceInter.getAll();
    };

    @GetMapping("/byId/{id}")
    public CategoryRequest getCategoryById(@PathVariable Long id){
        return categoryServiceInter.getCategoryById(id);
    };

    @PutMapping("/update/{id}/{name}")
    public void putById(@PathVariable Long id,@PathVariable String name){
        categoryServiceInter.putById(id,name);
    };

    @DeleteMapping("/delete/{id}")
    void deleteCategoryById(@PathVariable Long id){
        categoryServiceInter.deleteCategoryById(id);
    };
}
