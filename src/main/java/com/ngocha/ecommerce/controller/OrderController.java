package com.ngocha.ecommerce.controller;

import com.ngocha.ecommerce.configuration.AppConstants;
import com.ngocha.ecommerce.payload.CartDto;
import com.ngocha.ecommerce.payload.OrderDto;
import com.ngocha.ecommerce.payload.response.OrderResponse;
import com.ngocha.ecommerce.service.CartService;
import com.ngocha.ecommerce.service.OrderService;
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
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/public/users/{email}/carts/{cartId}/payments/{paymentMethod}/order")
    public ResponseEntity<OrderDto> orderProducts(@PathVariable String email, @PathVariable Long cartId, @PathVariable String paymentMethod) {
        OrderDto orderDto = orderService.placeOrder(email, cartId, paymentMethod);

        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @GetMapping("/admin/orders")
    public ResponseEntity<OrderResponse> getOrders(@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                    @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                    @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_ORDERS_BY, required = false) String sortBy,
                                                    @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder
                                                    ) {
        OrderResponse orders = orderService.getAllOrders(pageNumber, pageSize, sortBy, sortOrder);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/public/users/{userEmail}/carts")
    public ResponseEntity<List<OrderDto>> getOrdersByUser(@PathVariable String userEmail) {
        List<OrderDto> orders = orderService.getOrdersByUser(userEmail);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/public/users/{email}/orders/{orderId}")
    public ResponseEntity<OrderDto> getOrderByUser(@PathVariable String email, @PathVariable Long orderId) {
        OrderDto order = orderService.getOrder(email, orderId);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/admin/users/{email}/orders/{orderId}/orderStatus/{orderStatus}")
    public ResponseEntity<OrderDto> updateStatusOrder(@PathVariable String email, @PathVariable Long orderId, @PathVariable String orderStatus) {
        OrderDto order =  orderService.updateOrder(email, orderId, orderStatus);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

}
