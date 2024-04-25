package com.SneakStyle.OrderService.Service.Impl;

import com.SneakStyle.OrderService.Repository.OrderItemRepository;
import com.SneakStyle.OrderService.Repository.OrderRepository;
import com.SneakStyle.OrderService.Service.OrderService;
import com.SneakStyle.OrderService.dto.OrderDto;
import com.SneakStyle.OrderService.dto.ProductDto;
import com.SneakStyle.OrderService.dto.UserDto;
import com.SneakStyle.OrderService.dto.enums.OrderStatus;
import com.SneakStyle.OrderService.exception.RecordNotFoundException;
import com.SneakStyle.OrderService.model.Order;
import com.SneakStyle.OrderService.model.OrderItem;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static com.SneakStyle.OrderService.dto.constants.ApiUrls.GET_PRODUCT_BY_ID;
import static com.SneakStyle.OrderService.dto.constants.ApiUrls.GET_USER_BY_ID;
import static com.SneakStyle.OrderService.util.Helper.executeRestCall;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final RestTemplate restTemplate;

    @Override
    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        Order order = toEntity(orderDto);
        order.setDate(LocalDate.now());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setStatus(true);

        if(order.getUserId() != null) {
            ResponseEntity<UserDto> user = executeRestCall(() -> restTemplate.getForEntity(
                    GET_USER_BY_ID + order.getUserId(), UserDto.class
            ));
            order.setUser(user.getBody());
        }
        else {
            throw new RecordNotFoundException("User not found");
        }

        List<OrderItem> orderItems = order.getOrderItems().stream()
                .peek(orderItem -> {
                    if(orderItem.getProductId() != null){
                        ResponseEntity<ProductDto> product = executeRestCall(() -> restTemplate.getForEntity(
                                GET_PRODUCT_BY_ID + orderItem.getProductId(), ProductDto.class
                        ));
                        orderItem.setProduct(product.getBody());
                    }
                    else {
                        throw new RecordNotFoundException("Product not found");
                    }
                }).toList();

        Double totalAmount = orderItems.stream()
                .mapToDouble(orderItem -> orderItem.getQuantity() * orderItem.getProduct().getPrice())
                .sum();
        order.setTotalAmount(totalAmount);
        Order createdOrder = orderRepository.save(order);

        List<OrderItem> orderItemList = createdOrder.getOrderItems();
        if (orderItemList != null && !orderItemList.isEmpty()) {
            orderItemList.forEach(orderItem -> {
                orderItem.setStatus(true);
                orderItem.setOrder(createdOrder);
                orderItemRepository.save(orderItem);
            });
            createdOrder.setOrderItems(orderItemList);
            orderRepository.save(createdOrder);
        }
        return toDto(order);
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
                .totalAmount(order.getTotalAmount())
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
                .totalAmount(orderDto.getTotalAmount())
                .status(orderDto.getStatus())
                .orderItems(orderDto.getOrderItems())
                .build();
    }
}
