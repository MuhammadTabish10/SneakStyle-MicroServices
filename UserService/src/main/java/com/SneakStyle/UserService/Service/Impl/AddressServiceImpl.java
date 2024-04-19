package com.SneakStyle.UserService.Service.Impl;

import com.SneakStyle.UserService.Repository.AddressRepository;
import com.SneakStyle.UserService.Repository.UserRepository;
import com.SneakStyle.UserService.Service.AddressService;
import com.SneakStyle.UserService.dto.AddressDto;
import com.SneakStyle.UserService.exception.RecordNotFoundException;
import com.SneakStyle.UserService.model.Address;
import com.SneakStyle.UserService.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public AddressDto save(AddressDto addressDto) {
        Address address = toEntity(addressDto);

        User user = userRepository.findById(address.getUser().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("User Not found at id => %d", address.getUser().getId())));

        address.setStatus(true);
        address.setUser(user);

        addressRepository.save(address);
        return toDto(address);
    }

    @Override
    public List<AddressDto> getAllAddressOfUser(Long userId) {
        List<Address> addressList = addressRepository.findByUserIdAndStatusIsTrue(userId);
        return addressList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AddressDto getUserAddressByName(Long id, String name) {
        Address address = addressRepository.findByNameAndUser_IdAndStatusIsTrue(name, id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Address Not found at User id => %d", id)));
        return toDto(address);
    }

    @Override
    @Transactional
    public AddressDto update(Long id, AddressDto addressDto) {
        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Address Not found at id => %d", id)));

        User user = userRepository.findById(addressDto.getUser().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("User Not found at id => %d", addressDto.getUser().getId())));

        existingAddress.setName(addressDto.getName());
        existingAddress.setAddress(addressDto.getAddress());
        existingAddress.setCity(addressDto.getCity());
        existingAddress.setCountry(addressDto.getCountry());
        existingAddress.setPostalCode(addressDto.getPostalCode());
        existingAddress.setUser(user);

        Address updatedAddress = addressRepository.save(existingAddress);
        return toDto(updatedAddress);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Address Not found at User id => %d", id)));
        addressRepository.setStatusWhereId(address.getId(), false);
    }

    @Override
    @Transactional
    public void setToActive(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Address Not found at User id => %d", id)));
        addressRepository.setStatusWhereId(address.getId(), true);
    }

    public AddressDto toDto(Address address){
        return AddressDto.builder()
                .id(address.getId())
                .name(address.getName())
                .address(address.getAddress())
                .city(address.getCity())
                .country(address.getCountry())
                .postalCode(address.getPostalCode())
                .user(address.getUser())
                .status(address.getStatus())
                .build();
    }

    public Address toEntity(AddressDto addressDto){
        return Address.builder()
                .id(addressDto.getId())
                .name(addressDto.getName())
                .address(addressDto.getAddress())
                .city(addressDto.getCity())
                .country(addressDto.getCountry())
                .postalCode(addressDto.getPostalCode())
                .user(addressDto.getUser())
                .status(addressDto.getStatus())
                .build();
    }
}
