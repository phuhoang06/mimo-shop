package org.mimoshop.mapper;


import org.mimoshop.model.Product;
import org.mimoshop.payload.response.ProductResponse;

public interface ProductMapper {
    ProductResponse toResponse(Product product);
    // Có thể thêm các phương thức map khác nếu cần:
    // Product toEntity(CreateProductRequest request);
}