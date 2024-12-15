package com.ngocha.ecommerce.service;

import com.ngocha.ecommerce.entity.Category;
import com.ngocha.ecommerce.payload.CategoryDto;
import com.ngocha.ecommerce.payload.CategoryResponse;

public interface CategoryService {
    Category create(CategoryDto categoryDto);
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize);
    Category getCategoryById(Long categoryId);
    Category updateCategory(Long categoryId, Category category);
    String deleteCategory(Long categoryId);

}
