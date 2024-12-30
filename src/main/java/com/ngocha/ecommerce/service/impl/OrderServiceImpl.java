package com.ngocha.ecommerce.service.impl;

import com.ngocha.ecommerce.entity.Cart;
import com.ngocha.ecommerce.entity.Order;
import com.ngocha.ecommerce.entity.Payment;
import com.ngocha.ecommerce.exception.APIException;
import com.ngocha.ecommerce.exception.ResourceNotFoundException;
import com.ngocha.ecommerce.payload.OrderDto;
import com.ngocha.ecommerce.payload.response.OrderResponse;
import com.ngocha.ecommerce.repository.CartRepository;
import com.ngocha.ecommerce.repository.OrderItemRepository;
import com.ngocha.ecommerce.repository.OrderRepository;
import com.ngocha.ecommerce.repository.PaymentRepository;
import com.ngocha.ecommerce.service.CartService;
import com.ngocha.ecommerce.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentRepository paymentRepository;
    private  final ModelMapper modelMapper;
    private final CartService cartService;

    @Override
    public OrderDto placeOrder(String email, Long cartId, String paymentMethod) {
        Cart cart = cartRepository.findCartByEmailAndCartId(email, cartId);

        if(cart == null) {
            throw new ResourceNotFoundException("Cart", "cartId", cartId);
        }

        Order order = new Order();

        order.setEmail(email);
        order.setOrderDate(LocalDate.now());
        order.setTotalAmount(cart.getTotalPrice());
        order.setOrderStatus("Order Accepted");

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentMethod(paymentMethod);

        payment = paymentRepository.save(payment);

        order.setPayment(payment);

        Order savedOrder = orderRepository.save(order);
        return null;
    }

    @Override
    public OrderDto getOrder(String email, Long orderId) {
        Order order = orderRepository.findOrderByEmailAndOrderId(email, orderId);

        if(order == null) {
            throw new ResourceNotFoundException("Order", "orderId", orderId);
        }

        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public List<OrderDto> getOrdersByUser(String email) {
        List<Order> orders = orderRepository.findAllByEmail(email);

        List<OrderDto> orderDtos = orders.stream().map(order ->
            modelMapper.map(order, OrderDto.class)
        ).toList();

        if(orderDtos.isEmpty()) {
            throw new APIException("No orders placed yet by the user with email: " + email);
        }
        return orderDtos;
    }

    @Override
    public OrderResponse getAllOrders(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<Order> pageOrders = orderRepository.findAll(pageDetails);

        List<Order> orders = pageOrders.getContent();

        List<OrderDto> orderDtos = orders.stream().map(order ->
                modelMapper.map(order, OrderDto.class)).toList();

        if(orderDtos.isEmpty()) {
            throw new APIException("No orders placed yet by the users");
        }

        OrderResponse orderResponse = new OrderResponse();

        orderResponse.setContent(orderDtos);
        orderResponse.setPageNumber(pageOrders.getNumber());
        orderResponse.setPageSize(pageOrders.getSize());
        orderResponse.setTotalElements(pageOrders.getTotalElements());
        orderResponse.setTotalPages(pageOrders.getTotalPages());
        orderResponse.setLastPage(pageOrders.isLast());

        return orderResponse;
    }

    @Override
    public OrderDto updateOrder(String email, Long orderId, String orderStatus) {
        Order order = orderRepository.findOrderByEmailAndOrderId(email, orderId);

        if (order == null) {
            throw new ResourceNotFoundException("Order", "orderId", orderId);
        }

        order.setOrderStatus(orderStatus);

        return modelMapper.map(order, OrderDto.class);
    }
}
