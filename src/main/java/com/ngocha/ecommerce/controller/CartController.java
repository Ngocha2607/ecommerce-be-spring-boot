package com.ngocha.ecommerce.controller;

import com.ngocha.ecommerce.payload.CartDto;
import com.ngocha.ecommerce.service.CartService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@SecurityRequirement(name = "E-Commerce Application")
public class CartController {
    private final CartService cartService;

    @PostMapping("/public/carts/{cartId}/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDto> addProductToCart(@PathVariable Long cartId, @PathVariable Long productId, @PathVariable Integer quantity) {
        CartDto cartDto = cartService.addProductToCart(cartId, productId, quantity);

        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @GetMapping("/admin/carts")
    public ResponseEntity<List<CartDto>> getCarts() {
        List<CartDto> carts = cartService.getAllCarts();

        return new ResponseEntity<>(carts, HttpStatus.OK);
    }

    @GetMapping("/public/users/{userEmail}/carts/{cartId}")
    public ResponseEntity<CartDto> getCartById(@PathVariable String userEmail, @PathVariable Long cartId) {
        CartDto cartDto = cartService.getCart(userEmail, cartId);

        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @PutMapping("/public/carts/{cartId}/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDto> updateCartProduct(@PathVariable Long cartId, @PathVariable Long productId, @PathVariable Integer quantity) {
        CartDto cart = cartService.updateProductQuantityInCart(cartId, productId, quantity);

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping("/public/carts/{cartId}/products/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public String removeProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) {

        return cartService.deleteProductFromCart(cartId, productId);
    }

}
