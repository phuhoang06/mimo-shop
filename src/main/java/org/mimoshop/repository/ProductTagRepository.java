package org.mimoshop.repository;


import org.mimoshop.model.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  ProductTagRepository extends JpaRepository<ProductTag,Long> {
}