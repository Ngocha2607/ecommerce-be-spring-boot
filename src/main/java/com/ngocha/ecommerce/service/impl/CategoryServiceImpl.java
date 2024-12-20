package com.ngocha.ecommerce.service.impl;

import com.ngocha.ecommerce.entity.Category;
import com.ngocha.ecommerce.exception.APIException;
import com.ngocha.ecommerce.payload.CategoryDto;
import com.ngocha.ecommerce.payload.CategoryResponse;
import com.ngocha.ecommerce.repository.CategoryRepository;
import com.ngocha.ecommerce.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;


    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize) {
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize);

        Page<Category> pageCategories = categoryRepository.findAll(pageDetails);
        List<Category> categories = pageCategories.getContent();
        if(categories.isEmpty()) {
            throw new APIException("No Category Exist!");
        }

        List<CategoryDto> categoryDtos = categories.stream().map(category -> {
            return modelMapper.map(category, CategoryDto.class);
        }).collect(Collectors.toList());

        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setContent(categoryDtos);
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
