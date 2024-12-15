package com.ngocha.ecommerce.payload;

import com.ngocha.ecommerce.entity.Category;
import lombok.Data;

import java.util.List;

@Data
public class CategoryResponse {
    private List<Category> content;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private boolean lastPage;
}
