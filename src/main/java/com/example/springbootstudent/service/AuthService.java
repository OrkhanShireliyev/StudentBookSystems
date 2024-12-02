package com.example.springbootstudent.service;
import com.example.springbootstudent.dto.RegisterDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
  void register(RegisterDto registerDto);
}
