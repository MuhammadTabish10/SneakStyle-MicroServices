package com.SneakStyle.OrderService.dto;

import com.SneakStyle.OrderService.dto.enums.OrderStatus;
import com.SneakStyle.OrderService.model.OrderItem;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;

    @NotNull(message = "UserId cannot be null.")
    private Long userId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private OrderStatus orderStatus;
    private Boolean status;

    @NotNull(message = "Items cannot be null.")
    private List<OrderItem> orderItems;
}
