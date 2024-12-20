package com.ngocha.ecommerce.service.impl;

import com.ngocha.ecommerce.entity.Category;
import com.ngocha.ecommerce.entity.Product;
import com.ngocha.ecommerce.exception.APIException;
import com.ngocha.ecommerce.exception.ResourceNotFoundException;
import com.ngocha.ecommerce.payload.ProductDto;
import com.ngocha.ecommerce.payload.ProductResponse;
import com.ngocha.ecommerce.repository.CategoryRepository;
import com.ngocha.ecommerce.repository.ProductRepository;
import com.ngocha.ecommerce.service.FileService;
import com.ngocha.ecommerce.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FileService fileService;
    private final ModelMapper modelMapper;

    private String path = "/images";

    @Override
    public ProductDto store(Long categoryId, Product product) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        boolean isProductNotPresent = category.getProducts().stream()
                .noneMatch(existingProduct ->
                        existingProduct.getProductName().equals(product.getProductName()) &&
                                existingProduct.getDescription().equals(product.getDescription())
                );

        if (isProductNotPresent) {
            product.setImage("");
            product.setCategory(category);
            float specialPrice = (float) (product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice()));

            product.setSpecialPrice(specialPrice);

            Product savedProduct = productRepository.save(product);

            return modelMapper.map(savedProduct, ProductDto.class);
        } else {
            throw new APIException("Product already exists!");
        }

    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<Product> pageProducts = productRepository.findAll(pageDetails);

        List<Product> products = pageProducts.getContent();

        if (products.isEmpty()) {
            throw new APIException("No Product Exist!");
        }

        List<ProductDto> productDtos = products.stream().map(product -> {
            return modelMapper.map(product, ProductDto.class);
        }).collect(Collectors.toList());

        ProductResponse productResponse = new ProductResponse();

        productResponse.setContent(productDtos);
        productResponse.setPageNumber(pageProducts.getNumber());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalElements(pageProducts.getTotalElements());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setLastPage(pageProducts.isLast());

        return productResponse;
    }

    @Override
    public ProductDto getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(Long productId, Product product) {
        Product existProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        if (existProduct == null) {
            throw new APIException("Product not found with productId: " + productId);
        }
        existProduct.setProductId(productId);
        existProduct.setProductName(product.getProductName());
        existProduct.setDescription(product.getDescription());
        existProduct.setCategory(existProduct.getCategory());
        existProduct.setPrice(product.getPrice());
        existProduct.setImage(product.getImage());
        existProduct.setDiscount(product.getDiscount());
        existProduct.setQuantity(product.getQuantity());

        float specialPrice = (float) (product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice()));
        existProduct.setSpecialPrice(specialPrice);


        return modelMapper.map(existProduct, ProductDto.class);
    }

    @Override
    public String deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "productId", productId));

        productRepository.delete(product);
        return "User with productId" + productId + " deleted successfully!";
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<Product> pageProducts = productRepository.findAll(pageDetails);

        List<Product> products = pageProducts.getContent();

        if (products.isEmpty()) {
            throw new APIException(category.getCategoryName() + " category doesn't contain any product!");
        }

        List<ProductDto> productDtos = products.stream().map(product -> {
            return modelMapper.map(product, ProductDto.class);
        }).collect(Collectors.toList());

        ProductResponse productResponse = new ProductResponse();

        productResponse.setContent(productDtos);
        productResponse.setPageNumber(pageProducts.getNumber());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalElements(pageProducts.getTotalElements());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setLastPage(pageProducts.isLast());

        return productResponse;
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<Product> pageProducts = productRepository.findByProductNameLike(keyword, pageDetails);

        List<Product> products = pageProducts.getContent();

        if (products.isEmpty()) {
            throw new APIException("No Product Exist!");
        }

        List<ProductDto> productDtos = products.stream().map(product -> {
            return modelMapper.map(product, ProductDto.class);
        }).collect(Collectors.toList());

        ProductResponse productResponse = new ProductResponse();

        productResponse.setContent(productDtos);
        productResponse.setPageNumber(pageProducts.getNumber());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalElements(pageProducts.getTotalElements());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setLastPage(pageProducts.isLast());

        return productResponse;
    }

    @Override
    public ProductDto updateProductImage(Long productId, MultipartFile image) throws IOException {
        Product existProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        if (existProduct == null) {
            throw new APIException("Product not found with productId: " + productId);
        }

        String fileName = fileService.uploadImage(path, image);
        existProduct.setImage(fileName);

        return modelMapper.map(productRepository.save(existProduct), ProductDto.class);
    }


}
