package com.SneakStyle.OrderService.Repository;

import com.SneakStyle.OrderService.dto.enums.OrderStatus;
import com.SneakStyle.OrderService.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrderStatusAndStatusIsTrue(OrderStatus status);
    List<Order> findAllByStatusOrderByIdDesc(Boolean status);
    List<Order> findByDateAndStatusIsTrue(LocalDate date);
    List<Order> findByUserIdAndStatusIsTrue(Long userId);
    List<Order> findByDateBetweenAndStatusIsTrue(LocalDate startDate, LocalDate endDate);
    @Modifying
    @Query("UPDATE Order o SET o.status = :status WHERE o.id = :id")
    void setStatusWhereId(Long id, Boolean status);
}
