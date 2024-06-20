package com.example.demo.DTOs;

public record LoginResponseDTO(String accessToken, Long expiresIn, Object userData) {
}
