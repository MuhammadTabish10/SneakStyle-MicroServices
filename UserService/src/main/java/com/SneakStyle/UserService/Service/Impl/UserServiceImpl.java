package com.SneakStyle.UserService.Service.Impl;

import com.SneakStyle.UserService.Repository.UserRepository;
import com.SneakStyle.UserService.Service.UserService;
import com.SneakStyle.UserService.dto.UserDto;
import com.SneakStyle.UserService.exception.RecordNotFoundException;
import com.SneakStyle.UserService.exception.UserAlreadyExistAuthenticationException;
import com.SneakStyle.UserService.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public UserDto registerUser(UserDto userDto) {
        User user = toEntity(userDto);

        Optional<User> existingUser = userRepository.findByEmailAndStatusIsTrue(user.getEmail());
        if(existingUser.isPresent()){
            throw new UserAlreadyExistAuthenticationException("User Already Exist");
        }

        user.setStatus(true);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        User createdUser = userRepository.save(user);
        return toDto(createdUser);
    }

    @Override
    public List<UserDto> getAllUser(Boolean status) {
        List<User> users = userRepository.findAllByStatusOrderByIdDesc(status);
        return users.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("User Not found at id => %d", id)));
        return toDto(user);
    }

    @Override
    @Transactional
    public UserDto update(Long id, UserDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("User Not found at id => %d", id)));

        existingUser.setName(userDto.getName());
        existingUser.setPhone(existingUser.getPhone());

        User updatedUser = userRepository.save(existingUser);
        return toDto(updatedUser);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("User Not found at id => %d", id)));
        userRepository.setStatusWhereId(user.getId(), false);
    }

    @Override
    @Transactional
    public void setToActive(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("User Not found at id => %d", id)));
        userRepository.setStatusWhereId(user.getId(), true);
    }

    public UserDto toDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .createdAt(user.getCreatedAt())
                .password(user.getPassword())
                .status(user.getStatus())
                .build();
    }

    public User toEntity(UserDto userDto){
        return User.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .name(userDto.getName())
                .phone(userDto.getPhone())
                .createdAt(userDto.getCreatedAt())
                .password(userDto.getPassword())
                .status(userDto.getStatus())
                .build();
    }
}
