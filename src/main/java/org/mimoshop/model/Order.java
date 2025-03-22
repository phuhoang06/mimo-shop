package org.mimoshop.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String customerPhone;

    @Column(nullable = false)
    private String customerAddress;

    @Temporal(TemporalType.TIMESTAMP) // Lưu cả ngày và giờ đặt hàng
    @Column(nullable = false)
    private Date orderDate;

    @Column(nullable = false)
    private Double totalAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        orderDate = new Date(); // Set thời gian hiện tại khi tạo đơn hàng
    }
}
