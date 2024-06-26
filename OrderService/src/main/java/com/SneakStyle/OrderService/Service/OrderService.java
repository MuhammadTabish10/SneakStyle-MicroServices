package com.SneakStyle.OrderService.Service;

import com.SneakStyle.OrderService.dto.OrderDto;
import com.SneakStyle.OrderService.model.Order;

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
    void delete(Long id);
    void setToActive(Long id);
    void fetchOrderDetails(Order order);
}
