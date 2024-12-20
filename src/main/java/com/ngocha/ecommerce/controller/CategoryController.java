package com.ngocha.ecommerce.controller;

import com.ngocha.ecommerce.configuration.AppConstants;
import com.ngocha.ecommerce.entity.Category;
import com.ngocha.ecommerce.payload.CategoryResponse;
import com.ngocha.ecommerce.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@SecurityRequirement(name = "E-Commerce Application")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize
    ) {
        CategoryResponse categoryResponse = categoryService.getAllCategories(pageNumber, pageSize);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryId}")
    public ResponseEntity<Category> getCayegoryByID(@PathVariable Long categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping("/admin/categories/store")
    public ResponseEntity<Category> store(@RequestBody Category category) {
        Category storedCategory = categoryService.create(category);
        return new ResponseEntity<>(storedCategory, HttpStatus.OK);
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category, @PathVariable Long categoryId) {
        Category updatedCategory = categoryService.updateCategory(categoryId, category);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        String status = categoryService.deleteCategory(categoryId);

        return new ResponseEntity<>(status, HttpStatus.OK);
    }

}
