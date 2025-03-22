package org.mimoshop.service;


import org.mimoshop.exception.ResourceNotFoundException;
import org.mimoshop.model.Order;
import org.mimoshop.model.OrderItem;
import org.mimoshop.model.Product;
import org.mimoshop.repository.OrderItemRepository;
import org.mimoshop.repository.OrderRepository;
import org.mimoshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ProductRepository productRepository;


    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }
    public Order createOrder(Order order, Map<Long, Integer> productQuantities) {
        // 1. Kiểm tra null cho order
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        // 2. KHÔNG lưu Order ở đây.  Bỏ dòng này:
        // order = orderRepository.save(order);

        // 3. Kiểm tra null/empty cho productQuantities
        if (productQuantities == null || productQuantities.isEmpty()) {
            throw new IllegalArgumentException("Product quantities cannot be null or empty");
        }

        // 4. Lấy thông tin sản phẩm
        List<Long> productIds = productQuantities.keySet().stream().toList();
        List<Product> products = productRepository.findByIdIn(productIds);
        if (products.size() != productIds.size()) {
            throw new ResourceNotFoundException("One or more products not found");
        }
        Map<Long, Product> productMap = products.stream().collect(Collectors.toMap(Product::getId, product -> product));


        // 5. Tính totalAmount và *TẠO* OrderItem (nhưng CHƯA lưu)
        double totalAmount = 0.0;
        for (Long productId : productQuantities.keySet()) {
            Product product = productMap.get(productId);
            Integer quantity = productQuantities.get(productId);

            if (product == null) {
                throw new ResourceNotFoundException("Product with id " + productId + " not found");
            }
            if (quantity == null || quantity <= 0) {
                throw new IllegalArgumentException("Invalid quantity for product with id: " + productId);
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);  // Set Order (vẫn transient)
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setPrice(product.getPrice());
            // KHÔNG lưu OrderItem ở đây
            // orderItemRepository.save(orderItem);

            totalAmount += product.getPrice() * quantity;
        }

        // *SAU KHI* tính xong totalAmount:
        // 6. Set totalAmount cho Order
        order.setTotalAmount(totalAmount);
        // 7. Lưu Order (lúc này đã có totalAmount)
        Order savedOrder = orderRepository.save(order);

        // 8. *SAU KHI* Order đã được lưu (có ID), lưu các OrderItem:
        for (Long productId : productQuantities.keySet()) {
            Product product = productMap.get(productId);
            Integer quantity = productQuantities.get(productId);
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder); // Dùng savedOrder (đã có ID)
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setPrice(product.getPrice());
            orderItemRepository.save(orderItem);
        }

        return savedOrder; // Trả về Order đã lưu
    }
}