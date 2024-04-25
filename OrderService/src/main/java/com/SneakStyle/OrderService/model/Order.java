package com.SneakStyle.OrderService.model;

import com.SneakStyle.OrderService.dto.UserDto;
import com.SneakStyle.OrderService.dto.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private Double totalAmount;
    private Boolean status;

    @Transient
    private UserDto user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;


}
