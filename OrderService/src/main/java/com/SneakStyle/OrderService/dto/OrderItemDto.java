package com.SneakStyle.OrderService.dto;

import com.SneakStyle.OrderService.model.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private Long id;

    @NotNull(message = "ProductId cannot be null.")
    private Long productId;

    @NotNull(message = "Quantity cannot be null.")
    private Integer quantity;

    @NotNull(message = "Price cannot be null.")
    private Double pricePerItem;

    private Boolean status;

    @NotNull(message = "Order cannot be null.")
    private Order order;
}
