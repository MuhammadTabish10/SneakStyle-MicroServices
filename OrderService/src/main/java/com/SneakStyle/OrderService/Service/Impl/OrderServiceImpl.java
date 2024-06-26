package com.SneakStyle.OrderService.Service.Impl;

import com.SneakStyle.OrderService.Repository.OrderItemRepository;
import com.SneakStyle.OrderService.Repository.OrderRepository;
import com.SneakStyle.OrderService.Service.OrderService;
import com.SneakStyle.OrderService.dto.OrderDto;
import com.SneakStyle.OrderService.dto.ProductDto;
import com.SneakStyle.OrderService.dto.UserDto;
import com.SneakStyle.OrderService.dto.constants.Messages;
import com.SneakStyle.OrderService.dto.enums.OrderStatus;
import com.SneakStyle.OrderService.exception.RecordNotFoundException;
import com.SneakStyle.OrderService.model.Order;
import com.SneakStyle.OrderService.model.OrderItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.SneakStyle.OrderService.dto.constants.ApiUrls.GET_PRODUCT_BY_ID;
import static com.SneakStyle.OrderService.dto.constants.ApiUrls.GET_USER_BY_ID;
import static com.SneakStyle.OrderService.util.Helper.executeRestCall;

@Service
@Slf4j
@RequiredArgsConstructor
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

        if (order.getUserId() != null) {
            ResponseEntity<UserDto> user = executeRestCall(() -> restTemplate.getForEntity(
                    GET_USER_BY_ID + order.getUserId(), UserDto.class
            ));
            order.setUser(user.getBody());
        } else {
            throw new RecordNotFoundException(Messages.USER_NOT_FOUND);
        }

        double totalAmount = calculateTotalAmountAndSaveOrderItems(order);
        order.setTotalAmount(totalAmount);
        Order createdOrder = orderRepository.save(order);
        return toDto(createdOrder);
    }

    @Override
    public List<OrderDto> getAllByOrderStatus(String orderStatus) {
        List<Order> orders = orderRepository.findByOrderStatusAndStatusIsTrue(OrderStatus.PENDING);
        orders.forEach(this::fetchOrderDetails);
        return orders.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getAllOrders(Boolean status) {
        List<Order> orders = orderRepository.findAllByStatusOrderByIdDesc(status);
        orders.forEach(this::fetchOrderDetails);
        return orders.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getAllOrdersByDate(LocalDate date) {
        List<Order> orders = orderRepository.findByDateAndStatusIsTrue(date);
        orders.forEach(this::fetchOrderDetails);
        return orders.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getAllOrdersByUser(Long userId) {
        List<Order> orders = orderRepository.findByUserIdAndStatusIsTrue(userId);
        orders.forEach(this::fetchOrderDetails);
        return orders.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getAllOrdersBetweenDates(LocalDate startDate, LocalDate endDate) {
        List<Order> orders = orderRepository.findByDateBetweenAndStatusIsTrue(startDate, endDate);
        orders.forEach(this::fetchOrderDetails);
        return orders.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format(Messages.ORDER_NOT_FOUND, id)));
        fetchOrderDetails(order);
        return toDto(order);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format(Messages.ORDER_NOT_FOUND, id)));
        orderRepository.setStatusWhereId(order.getId(), false);
    }

    @Override
    @Transactional
    public void setToActive(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format(Messages.ORDER_NOT_FOUND, id)));
        orderRepository.setStatusWhereId(order.getId(), true);
    }

    @Override
    public void fetchOrderDetails(Order order) {
        if (order.getUserId() != null) {
            try {
                ResponseEntity<UserDto> user = executeRestCall(() -> restTemplate.getForEntity(
                        GET_USER_BY_ID + order.getUserId(), UserDto.class
                ));
                order.setUser(user.getBody());
            } catch (RestClientResponseException e) {
                log.warn(String.format(Messages.USER_NOT_FOUND_FOR_ORDER, order.getId()));
            }

            for (OrderItem orderItem : order.getOrderItems()) {
                if (orderItem.getProductId() != null) {
                    try {
                        ResponseEntity<ProductDto> product = executeRestCall(() -> restTemplate.getForEntity(
                                GET_PRODUCT_BY_ID + orderItem.getProductId(), ProductDto.class
                        ));
                        orderItem.setProduct(product.getBody());
                    } catch (RestClientResponseException e) {
                        log.warn(String.format(Messages.PRODUCT_NOT_FOUND_FOR_ORDER_ITEM, orderItem.getId()));
                    }
                } else {
                    log.warn(String.format(Messages.PRODUCT_ID_IS_NULL_FOR_ORDER_ITEM, orderItem.getId()));
                }
            }
        } else {
            log.warn(String.format(Messages.USER_ID_IS_NULL_FOR_ORDER, order.getId()));
        }
    }

    private double calculateTotalAmountAndSaveOrderItems(Order order) {
        double totalAmount = 0.0;
        for (OrderItem orderItem : order.getOrderItems()) {
            if (orderItem.getProductId() != null) {
                ResponseEntity<ProductDto> product = executeRestCall(() -> restTemplate.getForEntity(
                        GET_PRODUCT_BY_ID + orderItem.getProductId(), ProductDto.class
                ));
                orderItem.setProduct(product.getBody());
                orderItem.setStatus(true);
                totalAmount += orderItem.getQuantity() * orderItem.getProduct().getPrice();
                orderItem.setOrder(order);
                orderItemRepository.save(orderItem);
            } else {
                throw new RecordNotFoundException(Messages.PRODUCT_NOT_FOUND);
            }
        }
        return totalAmount;
    }

    private OrderDto toDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .orderStatus(order.getOrderStatus())
                .date(order.getDate())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .user(order.getUser())
                .orderItems(order.getOrderItems())
                .build();
    }

    private Order toEntity(OrderDto orderDto) {
        return Order.builder()
                .id(orderDto.getId())
                .userId(orderDto.getUserId())
                .orderStatus(orderDto.getOrderStatus())
                .date(orderDto.getDate())
                .totalAmount(orderDto.getTotalAmount())
                .status(orderDto.getStatus())
                .user(orderDto.getUser())
                .orderItems(orderDto.getOrderItems())
                .build();
    }
}