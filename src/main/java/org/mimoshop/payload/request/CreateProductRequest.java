package org.mimoshop.payload.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
@Getter
@Setter
public class CreateProductRequest {
    @NotBlank(message = "Product name is required")
    private String name;

    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be a positive value")
    private Double price;

    private String imageUrl;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    private List<String> tagNames; //sử dụng tên của tag
}