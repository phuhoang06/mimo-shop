package org.mimoshop.payload.request;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class UpdateProductRequest {

    private String name; // Không cần @NotBlank, vì có thể không cập nhật name
    private String description;

    @Positive(message = "Price must be greater than zero") // Chỉ validate nếu có giá trị
    private Double price;

    private String imageUrl;
    private Long categoryId; // Có thể null nếu không muốn update category
    private List<String> tagNames;
}