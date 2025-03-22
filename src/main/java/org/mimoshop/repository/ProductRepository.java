package org.mimoshop.repository;


import org.mimoshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Tìm kiếm sản phẩm theo tên (không phân biệt hoa thường)
    List<Product> findByNameContainingIgnoreCase(String name);

    // Tìm kiếm sản phẩm theo category ID, có phân trang
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    //Tìm kiếm theo danh sách id
    List<Product> findByIdIn(List<Long> productIds);
    
    // Các phương thức custom khác nếu cần
}