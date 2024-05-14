package com.example.demo.dto;

import com.example.demo.entity.Animal;


public record GlobalResponse(
        String status,
        String message,
        Object data)
{ }

