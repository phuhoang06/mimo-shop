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

    @NotBlank(message = "Customer name cannot be blank") // Validate không được rỗng
    private String customerName;

    @NotBlank(message = "Customer phone cannot be blank")
    @Pattern(regexp = "\\d{10,11}", message = "Invalid phone number") // Validate số điện thoại (10-11 chữ số)
    private String customerPhone;

    @NotBlank(message = "Customer address cannot be blank")
    private String customerAddress;

    @NotEmpty(message = "Product list cannot be empty")
    // Key: productId, Value: quantity
    private Map<Long, Integer> productQuantities;
}