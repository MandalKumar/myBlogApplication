package com.springboot.blog.controller;

import com.springboot.blog.payloads.CategoryDTO;
import com.springboot.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories/")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //Build add category rest API
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("createCategory")
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO categoryDTO) {
        return new ResponseEntity<>(categoryService.addCategory(categoryDTO), HttpStatus.CREATED);
    }

    @GetMapping("{id}/categoryId")
    public ResponseEntity<CategoryDTO> getDataBycategoryId(@PathVariable(name = "id") Long categoryId) {
        return new ResponseEntity<CategoryDTO>(categoryService.getCategoryById(categoryId), HttpStatus.OK);
    }

    @GetMapping("getAllCategory")
    public ResponseEntity<List<CategoryDTO>> getAllCategory() {
        return new ResponseEntity<List<CategoryDTO>>(categoryService.getAllCategory(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}/updateCategory")
    public ResponseEntity<CategoryDTO> updateCategoryById(@PathVariable(name = "id") Long categoryId, @RequestBody CategoryDTO categoryDTO) {
        return new ResponseEntity<CategoryDTO>(categoryService.updateCategoryById(categoryDTO, categoryId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}/deleteCategory")
    public ResponseEntity<String> deleteCategoryById(@PathVariable(name = "id") Long categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return new ResponseEntity<String>("Delete category successfully !!", HttpStatus.OK);
    }
}
