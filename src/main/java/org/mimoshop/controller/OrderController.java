package org.mimoshop.controller;


import jakarta.validation.Valid;
import org.mimoshop.mapper.OrderMapper;
import org.mimoshop.model.Order;
import org.mimoshop.model.OrderItem;
import org.mimoshop.payload.request.CreateOrderRequest;
import org.mimoshop.payload.response.OrderItemResponse;
import org.mimoshop.payload.response.OrderResponse;
import org.mimoshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*") // Chỉ nên cho phép các origin cụ thể, không nên dùng "*" trong production
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper; // Inject

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderResponse> orderResponses = orders.stream()
                .map(orderMapper::toResponse) // Dùng mapper
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        OrderResponse orderResponse = orderMapper.toResponse(order); // Dùng mapper
        return ResponseEntity.ok(orderResponse);
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        Order order = orderMapper.toEntity(request);  // Dùng mapper
        Order createdOrder = orderService.createOrder(order, request.getProductQuantities());
        OrderResponse orderResponse = orderMapper.toResponse(createdOrder);
        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }
}