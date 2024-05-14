package com.example.demo.dto;

import java.util.List;

public record GlobalPageableResponse(
        int currentPage,
        int totalPages,
        long totalElements,
        List<AnimalResponse> data
)
{ }
