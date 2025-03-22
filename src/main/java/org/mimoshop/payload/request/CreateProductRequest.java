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

    @NotBlank(message = "Product name cannot be blank")
    private String name;

    private String description; // Không bắt buộc

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    private Double price;

    private String imageUrl; // Không bắt buộc

    @NotNull(message = "Category ID cannot be null")
    private Long categoryId;
    private List<String> tagNames;
}