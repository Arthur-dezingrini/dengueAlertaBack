package com.example.demo.controllers;

import com.example.demo.DTOs.LoginDTO;
import com.example.demo.DTOs.LoginResponseDTO;
import com.example.demo.services.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class tokenController {

    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestParam LoginDTO login) {
        return loginService.verificaLogin(login);
    }
}
