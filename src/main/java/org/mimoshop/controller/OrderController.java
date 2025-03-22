package org.mimoshop.controller;


import jakarta.validation.Valid;
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
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;
     @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrder()
    {
        List<Order> orders=orderService.getAllOrders();
        List<OrderResponse> orderResponses=orders.stream().map(this::convertToOrderResponse).collect(Collectors.toList());
        return ResponseEntity.ok(orderResponses);
    }
    @GetMapping("/{id}")
    public  ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id)
    {
        Order order=orderService.getOrderById(id);
        OrderResponse orderResponse=convertToOrderResponse(order);
        return ResponseEntity.ok(orderResponse);
    }
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        Order order = new Order();
        order.setCustomerName(request.getCustomerName());
        order.setCustomerPhone(request.getCustomerPhone());
        order.setCustomerAddress(request.getCustomerAddress());

        Order createdOrder = orderService.createOrder(order, request.getProductQuantities());
        OrderResponse orderResponse = convertToOrderResponse(createdOrder);
        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }
    private OrderResponse convertToOrderResponse(Order order)
    {
        OrderResponse orderResponse=new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setCustomerName(order.getCustomerName());
        orderResponse.setCustomerAddress(order.getCustomerAddress());
        orderResponse.setCustomerPhone(order.getCustomerPhone());
        orderResponse.setOrderDate(order.getOrderDate());
        orderResponse.setTotalAmount(order.getTotalAmount());

        List<OrderItemResponse> orderItemResponses = order.getOrderItems().stream()
                .map(this::convertToOrderItemResponse)
                .collect(Collectors.toList());
        orderResponse.setOrderItems(orderItemResponses);
        return orderResponse;

    }
    private OrderItemResponse convertToOrderItemResponse(OrderItem orderItem) {
        OrderItemResponse itemResponse = new OrderItemResponse();
        itemResponse.setId(orderItem.getId());
        itemResponse.setProductId(orderItem.getProduct().getId());
        itemResponse.setProductName(orderItem.getProduct().getName()); // Lấy tên sản phẩm
        itemResponse.setQuantity(orderItem.getQuantity());
        itemResponse.setPrice(orderItem.getPrice());
        return itemResponse;
    }

    // Thường thì không update/delete đơn hàng (hoặc cần logic phức tạp)
}