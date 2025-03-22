package org.mimoshop.repository;

import org.mimoshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
     // Tìm category theo tên (không phân biệt hoa thường)
    Category findByNameIgnoreCase(String name);
}