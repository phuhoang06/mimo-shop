package org.mimoshop.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mimoshop.model.Order;
import org.mimoshop.model.OrderItem;
import org.mimoshop.payload.request.CreateOrderRequest;
import org.mimoshop.payload.response.OrderItemResponse;
import org.mimoshop.payload.response.OrderResponse;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper {

    @Mapping(source = "orderItems", target = "orderItems") // Thêm mapping
    OrderResponse toResponse(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderDate", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    Order toEntity(CreateOrderRequest request);
    // OrderItemResponse
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName") // Thêm để lấy productName

    OrderItemResponse toOrderItemResponse(OrderItem orderItem);
    //List<OrderItemResponse> toOrderItemResponseList(List<OrderItem> orderItems); // Không cần thiết vì đã có @Mapping(source = "orderItems", target = "orderItems")

}