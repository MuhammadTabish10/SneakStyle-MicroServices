package com.SneakStyle.UserService.Service;

import com.SneakStyle.UserService.dto.AddressDto;

import java.util.List;

public interface AddressService {
    AddressDto save(AddressDto addressDto);
    List<AddressDto> getAllAddressOfUser(Long id);
    AddressDto getUserAddressByName(Long id, String name);
    AddressDto update(Long id, AddressDto addressDto);
    void delete(Long id);
    void setToActive(Long id);
}
