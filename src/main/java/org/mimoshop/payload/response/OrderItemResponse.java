package org.mimoshop.payload.response;

    import lombok.Getter;
    import lombok.Setter;

    @Getter
    @Setter
    public class OrderItemResponse {
        private Long id;
        private Long productId;
        private String productName; // Thêm thông tin product name
        private Integer quantity;
        private Double price;
    }