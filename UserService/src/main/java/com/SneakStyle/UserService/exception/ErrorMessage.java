package com.SneakStyle.UserService.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorMessage<T> {
    T error;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime time;
}
