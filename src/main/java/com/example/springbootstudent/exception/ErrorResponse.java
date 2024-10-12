package com.example.springbootstudent.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ErrorResponse {

    private HttpStatus httpStatus;
    private int httpCode;
    private String message;

}
