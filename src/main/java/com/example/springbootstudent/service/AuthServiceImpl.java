package com.example.springbootstudent.service;

import com.example.springbootstudent.dto.RegisterDto;
import com.example.springbootstudent.model.User;
import com.example.springbootstudent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public void register(RegisterDto registerDto) {
    User user = new User();
    user.setUsername(registerDto.getUsername());
    user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
    userRepository.save(user);
  }
}
