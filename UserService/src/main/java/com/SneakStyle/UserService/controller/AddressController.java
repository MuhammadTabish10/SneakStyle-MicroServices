package com.SneakStyle.UserService.controller;

import com.SneakStyle.UserService.Service.AddressService;
import com.SneakStyle.UserService.dto.AddressDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/address")
    public ResponseEntity<AddressDto> addAddress(@Valid @RequestBody AddressDto addressDto){
        AddressDto address = addressService.save(addressDto);
        return ResponseEntity.ok(address);
    }
    @GetMapping("/address/{userId}")
    public ResponseEntity<List<AddressDto>> getAllAddressByUser(@PathVariable(name = "userId") Long userId){
        List<AddressDto> addresses = addressService.getAllAddressOfUser(userId);
        return ResponseEntity.ok(addresses);
    }
    @GetMapping("/address/{id}")
    public ResponseEntity<AddressDto> getAddressByNameAndUserId(@PathVariable Long id,
                                               @RequestParam(name = "userName") String userName){
        AddressDto addressDto = addressService.getUserAddressByName(id, userName);
        return ResponseEntity.ok(addressDto);
    }
    @DeleteMapping("/address/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id){
        addressService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/address/{id}")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable Long id, @RequestBody AddressDto addressDto) {
        AddressDto address = addressService.update(id, addressDto);
        return ResponseEntity.ok(address);
    }

    @PutMapping("/address/{id}/status")
    public ResponseEntity<Void> setAddressStatusToActiveById(@PathVariable Long id) {
        addressService.setToActive(id);
        return ResponseEntity.ok().build();
    }
}
