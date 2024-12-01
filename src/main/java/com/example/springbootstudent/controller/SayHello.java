package com.example.springbootstudent.controller;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SayHello {
    private final MessageSource messageSource;

    @GetMapping("/langChange")
    public String sayHello(){
        return messageSource.getMessage("hello",null, LocaleContextHolder.getLocale());
    }
}
