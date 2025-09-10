package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.JwtResponseDto;
import com.codewithmosh.store.dtos.LoginRequest;
import com.codewithmosh.store.exceptions.InvalidPasswordException;
import com.codewithmosh.store.exceptions.UserNotFoundException;
import com.codewithmosh.store.services.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager authenticationManager;
private final JwtService jwtService;
  @PostMapping("/login")
  public ResponseEntity<JwtResponseDto> login(
          @Valid @RequestBody LoginRequest loginRequest) {
   authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    loginRequest.email(),
                    loginRequest.password()
            )
    );
  var token = jwtService.generateToken(loginRequest.email());
    return ResponseEntity.ok(new JwtResponseDto(token));
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<Void> handleBadCredentialsException() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }
}
