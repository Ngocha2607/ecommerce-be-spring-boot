package com.ngocha.ecommerce.service;

import com.ngocha.ecommerce.payload.OrderDto;
import com.ngocha.ecommerce.payload.response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderDto placeOrder(String email, Long cartId, String paymentMethod);

    OrderDto getOrder(String email, Long orderId);

    List<OrderDto> getOrdersByUser(String email);

    OrderResponse getAllOrders(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    OrderDto updateOrder(String email, Long orderId, String orderStatus);
}
