package com.SneakStyle.OrderService.Service;

import com.SneakStyle.OrderService.dto.OrderDto;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);
    List<OrderDto> getAllByOrderStatus(String orderStatus);
    List<OrderDto> getAllOrders(Boolean status);
    List<OrderDto> getAllOrdersByDate(LocalDate date);
    List<OrderDto> getAllOrdersByUser(Long userId);
    List<OrderDto> getAllOrdersBetweenDates(LocalDate startDate, LocalDate endDate);
    OrderDto getOrderById(Long id);
    OrderDto update(Long id, OrderDto orderDto);
    void delete(Long id);
    void setToActive(Long id);
}
