package org.mimoshop.payload.request;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
@Getter
@Setter
public class UpdateProductRequest {
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private Long categoryId;
    private List<String> tagNames;
}