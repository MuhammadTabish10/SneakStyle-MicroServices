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

    private Boolean status;

    private ProductDto product;
    private Order order;
}
