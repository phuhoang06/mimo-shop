package org.mimoshop.mapper;


import org.mimoshop.model.Product;
import org.mimoshop.model.Tag;
import org.mimoshop.payload.response.ProductResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component // Đánh dấu để Spring quản lý
public class ProductMapperImpl implements ProductMapper {
    @Override
    public ProductResponse toResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setImageUrl(product.getImageUrl());
        response.setCategoryId(product.getCategory().getId());
        response.setCategoryName(product.getCategory().getName());

        if (product.getTags() != null) {
            List<String> tagNames = product.getTags().stream()
                    .map(Tag::getName)
                    .collect(Collectors.toList());
            response.setTagNames(tagNames);
        }
        return response;
    }
}