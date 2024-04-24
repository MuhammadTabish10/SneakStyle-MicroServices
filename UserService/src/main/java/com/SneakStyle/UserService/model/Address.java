package com.SneakStyle.UserService.model;

import com.SneakStyle.UserService.dto.enums.AddressType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AddressType name;

    private String address;
    private String city;
    private String country;
    private String postalCode;
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
