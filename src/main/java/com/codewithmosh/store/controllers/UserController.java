package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.UserDto;
import com.codewithmosh.store.dtos.UserDtoRequest;
import com.codewithmosh.store.entities.User;
import com.codewithmosh.store.mappers.UserMapper;
import com.codewithmosh.store.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  @GetMapping
  public ResponseEntity<List<UserDto>> getAllUsers(
          @RequestParam(required = false, defaultValue = "") String sort
  ) {
    if (!Set.of("name", "email").contains(sort)) {
      sort = "name";
    }
    List<UserDto> users = userRepository.findAll(Sort.by(sort))
            .stream()
            .map(userMapper::toDto)
            .toList();
    return ResponseEntity.ok(users);
  }

  @PostMapping
  public ResponseEntity<?> registerUser(@Valid @RequestBody UserDtoRequest userDtoRequest) {

    if(userRepository.existsByEmail(userDtoRequest.email())){
      return  ResponseEntity.badRequest().body(
              Map.of("Email","Email already registered")
      );
    };
    User user = userMapper.fromDto(userDtoRequest);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    User createdUser = userRepository.save(user);
    return ResponseEntity.ok(userMapper.toDto(createdUser));
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserDto> updateUser(
          @PathVariable Long id,
          @RequestBody UserDtoRequest request
          ) {
    User user = userRepository.findById(id).orElse(null);
    if (user == null) {
      return ResponseEntity.notFound().build();
    }
    user = userMapper.update(user, request);
    return ResponseEntity.ok(userMapper.toDto(user));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(
          @PathVariable Long id
  ){
    User user = userRepository.findById(id).orElse(null);
    if (user == null) {
      return ResponseEntity.notFound().build();
    }
    userRepository.deleteById(id);
    return  ResponseEntity.noContent().build();
  }
}
