package com.ngocha.ecommerce.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long productId;

    private String productName;

    private String description;

    private Float discount;

    private String image;

    private Float price;

    private Long quantity;

    private Float specialPrice;
}
