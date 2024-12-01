package com.example.springbootstudent.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocaleHolder {
    private Local currentLocal;
}
