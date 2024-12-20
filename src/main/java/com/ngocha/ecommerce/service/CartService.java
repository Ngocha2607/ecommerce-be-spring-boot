package com.ngocha.ecommerce.service;

import com.ngocha.ecommerce.payload.CartDto;

import java.util.List;

public interface CartService {

    CartDto addProductToCart(Long cartId, Long productId, Integer quantity);

    List<CartDto> getAllCarts();

    CartDto getCart(String userEmail, Long cartId);

    CartDto updateProductQuantityInCart(Long cartId, Long productId, Integer quatity);

    void updateProductInCart(Long cartId, Long productId);

    String deleteProductFromCart(Long cartId, Long productId);

}
