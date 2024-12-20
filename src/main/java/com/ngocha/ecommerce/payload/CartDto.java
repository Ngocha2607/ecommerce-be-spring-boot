package com.ngocha.ecommerce.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private Long cartId;
    private Float totalPrice = 0.0F;
    private List<ProductDto> products = new ArrayList<>();
}
