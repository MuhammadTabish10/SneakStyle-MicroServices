package com.SneakStyle.OrderService.controller;

import com.SneakStyle.OrderService.Service.OrderService;
import com.SneakStyle.OrderService.dto.OrderDto;
import com.SneakStyle.OrderService.dto.enums.OrderStatus;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
public class OrderController {

    private OrderService orderService;
    @PostMapping("/order")
//    @CircuitBreaker(name = "addOrderBreaker", fallbackMethod = "addOrderFallBack")
    public ResponseEntity<OrderDto> addOrder(@Valid @RequestBody OrderDto orderDto) {
        OrderDto order = orderService.createOrder(orderDto);
        return ResponseEntity.ok(order);
    }
    public ResponseEntity<OrderDto> addOrderFallBack(OrderDto orderDto, Exception ex){
        log.info("Fallback is executed because service is down: ", ex.getMessage());
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }
    @GetMapping("/order")
    public ResponseEntity<List<OrderDto>> getAllOrdersByOrderStatus(@RequestParam(name = "status") String status) {
        List<OrderDto> orderDtoList = orderService.getAllByOrderStatus(status);
        return ResponseEntity.ok(orderDtoList);
    }
    @GetMapping("/order/status/{status}")
    public ResponseEntity<List<OrderDto>> getAllOrders(@PathVariable Boolean status) {
        List<OrderDto> orderDtoList = orderService.getAllOrders(status);
        return ResponseEntity.ok(orderDtoList);
    }
    @GetMapping("/order/date/{date}")
    public ResponseEntity<List<OrderDto>> getAllOrdersByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<OrderDto> orderDtoList = orderService.getAllOrdersByDate(date);
        return ResponseEntity.ok(orderDtoList);
    }
    @GetMapping("/order/dates")
    public ResponseEntity<List<OrderDto>> getAllOrdersBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<OrderDto> orderDtoList = orderService.getAllOrdersBetweenDates(startDate, endDate);
        return ResponseEntity.ok(orderDtoList);
    }
    @GetMapping("/order/{id}")
    @CircuitBreaker(name = "getOrderByIdBreaker", fallbackMethod = "getOrderByIdFallBack")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        OrderDto order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }
    public ResponseEntity<OrderDto> getOrderByIdFallBack(Long id, Throwable ex){
        log.info("Fallback is executed because service is down: ", ex.getMessage());
        OrderDto errorOrderDto = OrderDto.builder()
                .id(id)
                .orderStatus(OrderStatus.ERROR)
                .totalAmount(0.0)
                .date(LocalDate.now())
                .status(false)
                .build();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorOrderDto);
    }
    @DeleteMapping("/order/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.ok().build();
    }
//    @PutMapping("/order/{id}")
//    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @RequestBody OrderDto orderDto) {
//        OrderDto order = orderService.update(id, orderDto);
//        return ResponseEntity.ok(order);
//    }
    @PutMapping("/order/{id}/status")
    public ResponseEntity<Void> setOrderStatusToActiveById(@PathVariable Long id) {
        orderService.setToActive(id);
        return ResponseEntity.ok().build();
    }
}
