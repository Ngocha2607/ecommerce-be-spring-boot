package com.ngocha.ecommerce.service;

import com.ngocha.ecommerce.entity.Product;
import com.ngocha.ecommerce.payload.ProductDto;
import com.ngocha.ecommerce.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    ProductDto store(Long categoryId, Product product);

    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductDto getProductById(Long productId);

    ProductDto updateProduct(Long productId, Product product);

    String deleteProduct(Long productId);

    ProductResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductDto updateProductImage(Long productId, MultipartFile image) throws IOException;
}
