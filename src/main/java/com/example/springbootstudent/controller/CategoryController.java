package com.example.springbootstudent.controller;

import com.example.springbootstudent.controller.request.CategoryRequest;
import com.example.springbootstudent.dto.CategoryDTO;
import com.example.springbootstudent.exception.NotFoundException;
import com.example.springbootstudent.service.inter.CategoryServiceInter;
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
@RequestMapping("/category")
@Slf4j
@Tag(name = "Category Controller", description = "Operations related to category management")
public class CategoryController {

    private final CategoryServiceInter categoryServiceInter;

    public CategoryController(CategoryServiceInter categoryServiceInter) {
        this.categoryServiceInter = categoryServiceInter;
    }

    @Operation(summary = "Save category", description = "Fill category information and save it!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully saved!"),
            @ApiResponse(responseCode = "500", description = "Can't save category!")
    })
    @PostMapping("/save")
    public ResponseEntity<CategoryRequest> save(@RequestBody CategoryRequest categoryRequest){
        try {
            categoryServiceInter.save(categoryRequest);
            log.info("Successfully created {}",categoryRequest);
            return new ResponseEntity<>(categoryRequest, HttpStatus.OK);
        }catch (Exception e){
            log.error("Error happened at trying creating categories!");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    };

    @Operation(summary = "Get all categories", description = "Get all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The category was not found")
    })
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAll(){
        List<CategoryDTO> categoryDTOS=categoryServiceInter.getAll();
        if (categoryDTOS==null){
            log.info("CategoryDTOS is null!");
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }else {
            log.info("Successfully retrieved{}",categoryDTOS);
            return new ResponseEntity<>(categoryDTOS, HttpStatus.OK);
        }
    };

    @Operation(summary = "Get a category by id", description = "Returns a category as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The category was not found")
    })
    @GetMapping("/byId/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id){
        CategoryDTO categoryDTO=categoryServiceInter.getCategoryById(id);
        if (categoryDTO==null){
            log.info("CategoryDTO is null!");
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }else {
            log.info("Successfully retrieved{}",categoryDTO);
            return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
        }
    };

    @Operation(summary = "Update category", description = "Fill category for change category's info and update it!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated!"),
            @ApiResponse(responseCode = "500", description = "Can't update category!")
    })
    @PutMapping("/update/{id}/{name}")
    public ResponseEntity<CategoryDTO> putById(@PathVariable Long id,@PathVariable String name){
        CategoryDTO categoryDTO=categoryServiceInter.getCategoryById(id);
        if (categoryDTO==null) {
            throw new NotFoundException("Not found category with id="+id);
        }

        try{
            categoryServiceInter.putById(id,name);
            log.info("Successfully updated{}",categoryDTO);
            return new ResponseEntity<>(categoryDTO,HttpStatus.OK);
        }catch (Exception e){
            log.error("Error happened at trying updating category!");
            return new ResponseEntity<>(categoryDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    };

    @Operation(summary = "Delete a category by id", description = "Delete a category as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Can't delete category!")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CategoryDTO> deleteCategoryById(@PathVariable Long id){
        CategoryDTO categoryDTO=categoryServiceInter.getCategoryById(id);
        if (categoryDTO==null) {
            throw new NotFoundException("Not found category with id="+id);
        }

        try{
            categoryServiceInter.deleteCategoryById(id);
            log.info("Successfully deleted{}",categoryDTO);
            return new ResponseEntity<>(categoryDTO,HttpStatus.OK);
        }catch (Exception e){
            log.error("Error happened at trying deleting category!");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    };
}
