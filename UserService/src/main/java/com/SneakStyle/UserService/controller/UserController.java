package com.SneakStyle.UserService.controller;

import com.SneakStyle.UserService.Service.UserService;
import com.SneakStyle.UserService.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/user")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto){
        UserDto user = userService.registerUser(userDto);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/user")
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(name = "status") Boolean status){
        List<UserDto> users = userService.getAllUser(status);
        return ResponseEntity.ok(users);
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/user/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        UserDto user = userService.update(id, userDto);
        return ResponseEntity.ok(user);
    }
    @PutMapping("/user/{id}/status")
    public ResponseEntity<Void> setUserStatusToActiveById(@PathVariable Long id) {
        userService.setToActive(id);
        return ResponseEntity.ok().build();
    }
}
