package com.springboot.blog.service;

import com.springboot.blog.payloads.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO addCategory(CategoryDTO categoryDTO);
    CategoryDTO getCategoryById(Long categoryId);
    List<CategoryDTO> getAllCategory();
    CategoryDTO updateCategoryById(CategoryDTO categoryDTO,Long categoryId);
    void deleteCategoryById(Long categoryId);
}
