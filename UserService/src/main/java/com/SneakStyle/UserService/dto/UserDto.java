package com.SneakStyle.UserService.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;

    @NotBlank(message = "Name cannot be null.")
    private String name;

    @Email(message = "please Enter a correct email.")
    @NotBlank(message = "Name cannot be null.")
    private String email;

    @NotBlank(message = "Phone cannot be null.")
    @Pattern(regexp = "^\\d{11}$", message = "Phone number must be 11 digits.")
    private String phone;

    @NotBlank(message = "Password cannot be null.")
    private String password;

    private Boolean status;
}
