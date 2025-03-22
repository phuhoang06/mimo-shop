package org.mimoshop.payload.response;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
@Getter
@Setter
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private Long categoryId;
    private String categoryName; // Thêm thông tin category name
    private List<String> tagNames; // Return tag names instead of tag objects

    // Constructor, getters, setters (hoặc dùng Lombok)
}