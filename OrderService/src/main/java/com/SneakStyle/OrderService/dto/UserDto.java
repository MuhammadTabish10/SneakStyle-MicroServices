package com.SneakStyle.OrderService.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private LocalDateTime createdAt;
    private String name;
    private String email;
    private String phone;
    private String password;
    private Boolean status;
}
