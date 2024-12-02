package com.example.springbootstudent.controller;

import com.example.springbootstudent.dto.LoginDto;
import com.example.springbootstudent.dto.RegisterDto;
import com.example.springbootstudent.security.JwtTokenProvider;

import com.example.springbootstudent.service.AuthService;
import com.example.springbootstudent.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager authenticationManager;

  private final CustomUserDetailsService userDetailsService;

  private final AuthService authService;

  private final JwtTokenProvider jwtTokenProvider;

  @Value("${jwtSecret}")
  String jwtSecret;

  @PostMapping("/login")
  public String login(@RequestBody LoginDto loginRequest) {
    Authentication authentication =
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

    SecurityContextHolder.getContext().setAuthentication(authentication);
    return jwtTokenProvider.generateToken(authentication);
  }

  @PostMapping("/register")
  public void register(@RequestBody RegisterDto registerDto) {
    authService.register(registerDto);
  }
}
