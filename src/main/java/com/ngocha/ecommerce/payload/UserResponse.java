package com.ngocha.ecommerce.payload;

import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    private List<UserDto> content;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private boolean lastPage;
}
