package com.example.demo.controllers;

import com.example.demo.DTOs.LoginDTO;
import com.example.demo.DTOs.LoginResponseDTO;
import com.example.demo.DTOs.UsuarioDTO;
import com.example.demo.models.Usuario;
import com.example.demo.services.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity<String> cadastrarUsuario (@RequestBody UsuarioDTO user) {
        return usuarioService.postUsuario(user);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO login) {
        return usuarioService.verificaLogin(login);
    }
}