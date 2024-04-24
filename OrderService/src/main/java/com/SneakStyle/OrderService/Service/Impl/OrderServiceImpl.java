package com.SneakStyle.OrderService.Service.Impl;

import com.SneakStyle.OrderService.Repository.OrderItemRepository;
import com.SneakStyle.OrderService.Repository.OrderRepository;
import com.SneakStyle.OrderService.Service.OrderService;
import com.SneakStyle.OrderService.dto.OrderDto;
import com.SneakStyle.OrderService.dto.enums.OrderStatus;
import com.SneakStyle.OrderService.exception.RecordNotFoundException;
import com.SneakStyle.OrderService.model.Order;
import com.SneakStyle.OrderService.model.OrderItem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        Order order = toEntity(orderDto);
        order.setDate(LocalDate.now());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setStatus(true);

        List<OrderItem> orderItems = order.getOrderItems().stream()
                .map(orderItem -> orderItemRepository.findById(orderItem.getId())
                        .orElseThrow(() -> new RecordNotFoundException(String.format("OrderItem not found at id => %d", orderItem.getId()))))
                .toList();

        return null;
    }

    @Override
    public List<OrderDto> getAllByOrderStatus(String orderStatus) {
        return null;
    }

    @Override
    public List<OrderDto> getAllOrders(Boolean status) {
        return null;
    }

    @Override
    public List<OrderDto> getAllOrdersByDate(LocalDate date) {
        return null;
    }

    @Override
    public List<OrderDto> getAllOrdersByUser(Long userId) {
        return null;
    }

    @Override
    public List<OrderDto> getAllOrdersBetweenDates(LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public OrderDto getOrderById(Long id) {
        return null;
    }

    @Override
    @Transactional
    public OrderDto update(Long id, OrderDto orderDto) {
        return null;
    }

    @Override
    @Transactional
    public void delete(Long id) {

    }

    @Override
    @Transactional
    public void setToActive(Long id) {

    }

    public OrderDto toDto(Order order){
        return OrderDto.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .orderStatus(order.getOrderStatus())
                .date(order.getDate())
                .status(order.getStatus())
                .orderItems(order.getOrderItems())
                .build();
    }

    public Order toEntity(OrderDto orderDto){
        return Order.builder()
                .id(orderDto.getId())
                .userId(orderDto.getUserId())
                .orderStatus(orderDto.getOrderStatus())
                .date(orderDto.getDate())
                .status(orderDto.getStatus())
                .orderItems(orderDto.getOrderItems())
                .build();
    }
}
