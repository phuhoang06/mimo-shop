package org.mimoshop.repository;


import org.mimoshop.model.Product;
import org.mimoshop.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String name);

    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    List<Product> findByTagsIn(List<Tag> tags); // Thêm phương thức tìm theo tags

    List<Product> findByIdIn(List<Long> ids); // THÊM DÒNG NÀY
}