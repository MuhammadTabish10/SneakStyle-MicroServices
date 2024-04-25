package com.SneakStyle.OrderService.controller;

import com.SneakStyle.OrderService.Service.OrderService;
import com.SneakStyle.OrderService.dto.OrderDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<OrderDto> addOrder(@Valid @RequestBody OrderDto orderDto) {
        OrderDto order = orderService.createOrder(orderDto);
        return ResponseEntity.ok(order);
    }
}
