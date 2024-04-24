package com.SneakStyle.UserService.dto;

import com.SneakStyle.UserService.dto.enums.AddressType;
import com.SneakStyle.UserService.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private Long id;

    @NotNull(message = "Name cannot be null.")
    private AddressType name;

    @NotBlank(message = "Address cannot be null.")
    private String address;

    @NotBlank(message = "City cannot be null.")
    private String city;

    @NotBlank(message = "Country cannot be null.")
    private String country;

    @NotBlank(message = "Postal Code cannot be null.")
    private String postalCode;

    private Boolean status;

    @NotNull(message = "User cannot be null.")
    private User user;
}
