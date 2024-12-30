package com.ngocha.ecommerce.payload;

import com.ngocha.ecommerce.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long orderId;

    private String email;

    private List<OrderItemDto> orderItems = new ArrayList<>();

    private LocalDate orderDate;

    private PaymentDto payment;

    private Float totalAmount;

    private String orderStatus;
}
