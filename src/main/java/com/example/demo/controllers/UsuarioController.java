package com.example.demo.controllers;

import com.example.demo.DTOs.UsuarioDTO;
import com.example.demo.models.Usuario;
import com.example.demo.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrarUsuario (@RequestBody UsuarioDTO user) {
        return usuarioService.postUsuario(new Usuario(user));
    }

//    @GetMapping()
//    public ResponseEntity<String> listarUsuario (Long cpf) {
//        return usuarioService.getUsuario(cpf);
//    }
}