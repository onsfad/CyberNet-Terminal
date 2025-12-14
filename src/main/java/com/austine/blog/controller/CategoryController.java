package com.austine.blog.controller;

import com.austine.blog.MessageUtil.ApiResponse;
import com.austine.blog.MessageUtil.CustomMessages;
import com.austine.blog.exceptions.RecordNotFoundException;
import com.austine.blog.model.Category;
import com.austine.blog.model.Post;
import com.austine.blog.service.AppService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private AppService service;



    @ApiOperation("To Create a Category record")
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addPost(@RequestBody Category category){
        category.setDateCreated(new Date());
        Category record= service.getCategoryRepository().save(category);
        return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success, record));
    }

    @ApiOperation("To return all Categories")
    @GetMapping("/allCategories")
    public List<Category> getCategories(){
        return service.getCategoryRepository().findAll();
    }

    @ApiOperation("To return a Category by ID")
    @GetMapping("/category/{id}")
    public ResponseEntity getCategoryById(@PathVariable("id") Long id){
        return service.getCategoryRepository().findById(id).map(record->{
            return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success, record));
        }).orElseThrow(()-> new RecordNotFoundException("No Category Found for: "+ id));
    }

    @ApiOperation("To delete a Category by ID")
    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity deleteCategoryById(@PathVariable("id")Long id){
        return service.getCategoryRepository().findById(id)
                .map(record-> {
                    service.getCategoryRepository().deleteById(id);
                    return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Deleted, CustomMessages.DeletedMessage));
                }).orElseThrow(()-> new RecordNotFoundException("No Category Found for: "+ id));
    }
}
