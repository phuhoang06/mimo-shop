package org.mimoshop.payload.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CreateOrderRequest {

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Customer phone is required")
    @Pattern(regexp = "\\d{10}", message = "Invalid phone number format") // Validate 10 digits
    private String customerPhone;

    @NotBlank(message = "Customer address is required")
    private String customerAddress;

    @NotEmpty(message = "Product quantities must not be empty") // Sửa thành @NotEmpty
    private Map<Long, Integer> productQuantities;
}
