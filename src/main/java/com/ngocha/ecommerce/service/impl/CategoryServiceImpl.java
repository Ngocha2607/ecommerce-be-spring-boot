package com.ngocha.ecommerce.service.impl;

import com.ngocha.ecommerce.entity.Category;
import com.ngocha.ecommerce.exception.APIException;
import com.ngocha.ecommerce.payload.CategoryDto;
import com.ngocha.ecommerce.payload.CategoryResponse;
import com.ngocha.ecommerce.payload.UserResponse;
import com.ngocha.ecommerce.repository.CategoryRepository;
import com.ngocha.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public Category create(CategoryDto categoryDto) {
        Category savedCategory = new Category();
        savedCategory.setCategoryName(categoryDto.getCategoryName());
        return categoryRepository.save(savedCategory);
    }

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize) {
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize);

        Page<Category> pageCategories = categoryRepository.findAll(pageDetails);
        List<Category> categories = pageCategories.getContent();
        if(categories.isEmpty()) {
            throw new APIException("No Category Exist!");
        }

        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setContent(categories);
        categoryResponse.setPageNumber(pageCategories.getNumber());
        categoryResponse.setPageSize(pageCategories.getSize());
        categoryResponse.setTotalElements(pageCategories.getTotalElements());
        categoryResponse.setTotalPages(pageCategories.getTotalPages());
        categoryResponse.setLastPage(pageCategories.isLast());

        return categoryResponse;
    }

    @Override
    public Category getCategoryById(Long categoryId) throws UsernameNotFoundException {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new UsernameNotFoundException("Category not found"));
    }

    @Override
    public Category updateCategory(Long categoryId, Category categoryDto) {

        Category existsCategory = categoryRepository.findById(categoryId).orElseThrow(() -> new UsernameNotFoundException("Category not found"));

        existsCategory.setCategoryName(categoryDto.getCategoryName());

        return existsCategory;
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new UsernameNotFoundException("Category not found"));

        categoryRepository.delete(category);
        return "Category with " + categoryId + " delete successfully!";
    }
}
