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

        // 2. Kiểm tra null/empty cho productQuantities
        if (productQuantities == null || productQuantities.isEmpty()) {
            throw new IllegalArgumentException("Product quantities cannot be null or empty");
        }

        // 3. Lấy thông tin sản phẩm và kiểm tra
        List<Long> productIds = productQuantities.keySet().stream().toList(); // Chuyển keySet thành List
        List<Product> products = productRepository.findByIdIn(productIds);

        if (products.size() != productIds.size()) {
            // Tìm ID sản phẩm không tìm thấy
            List<Long> foundProductIds = products.stream().map(Product::getId).collect(Collectors.toList());
            productIds.removeAll(foundProductIds); // Các ID không tìm thấy sẽ còn lại trong productIds
            throw new ResourceNotFoundException("Products not found with ids: " + productIds);
        }
        // Tạo Map để tra cứu sản phẩm theo ID cho nhanh
        Map<Long, Product> productMap = products.stream().collect(Collectors.toMap(Product::getId, product -> product));


        // 4. Tính totalAmount và tạo OrderItem (NHƯNG CHƯA LƯU OrderItem)
        double totalAmount = 0.0;
        for (Long productId : productQuantities.keySet()) {
            Product product = productMap.get(productId); // Lấy từ Map, đã kiểm tra tồn tại ở trên rồi
            Integer quantity = productQuantities.get(productId);


            if (quantity == null || quantity <= 0) {
                throw new IllegalArgumentException("Invalid quantity for product with id: " + productId);
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order); // Set Order (vẫn là transient)
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setPrice(product.getPrice()); // Lấy giá từ Product
            // KHÔNG lưu OrderItem ở đây

            totalAmount += product.getPrice() * quantity;
        }

        // 5. Set totalAmount cho Order *TRƯỚC KHI LƯU*
        order.setTotalAmount(totalAmount);

        // 6. *LƯU* Order (lúc này đã có totalAmount và id sẽ được generate)
        Order savedOrder = orderRepository.save(order);


        // 7. *SAU KHI* Order đã được lưu (có ID), *LƯU* các OrderItem:
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


        return savedOrder; // Trả về Order đã lưu (có ID, totalAmount, và các OrderItem)
    }
}