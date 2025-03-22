package org.mimoshop.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderResponse {
    private  Long id;
    private String customerName;
    private String customerPhone;
    private  String customerAddress;
    private Date orderDate;
    private  Double totalAmount;
    private List<OrderItemResponse> orderItems;

}