package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;

import java.util.List;


public interface CategoryService {
    CategoryResponse getAllCategories(Integer page, Integer pageSize, String sortBy, String sortOrder);
    CategoryDTO createCategory(CategoryDTO category);
    CategoryDTO deleteCategory(Long categoryID);
    CategoryDTO updateCategory(CategoryDTO category, Long categoryID);
}
