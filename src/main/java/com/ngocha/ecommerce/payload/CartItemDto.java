package com.ngocha.ecommerce.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private Long cartItem;
    private CartDto cart;
    private ProductDto product;
    private Integer quantity;
    private Float discount;
    private Float productPrice;
}
