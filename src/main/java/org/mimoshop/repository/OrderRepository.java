package org.mimoshop.repository;


import org.mimoshop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Các phương thức truy vấn custom nếu cần
}