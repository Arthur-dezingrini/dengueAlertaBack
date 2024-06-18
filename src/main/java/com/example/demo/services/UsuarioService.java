package com.example.demo.services;

import com.example.demo.DTOs.RegistrarNotificacaoDTO;
import com.example.demo.models.Notificacao;
import com.example.demo.models.Usuario;
import com.example.demo.repositories.notificacaoRepository;
import com.example.demo.repositories.usuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    usuarioRepository repository;

    public ResponseEntity<String> postUsuario(Usuario user) {
        try {
            if (repository.existsByCpf(user.getCpf())) {
                return ResponseEntity.status(500).body("CPF ja Cadastrado");
            } else {
                repository.save(user);
                return ResponseEntity.status(200).body("Usuario cadastrado com sucesso");
            }
        } catch (Exception e) {
            System.out.println("Erro" + e.getMessage());
            return ResponseEntity.status(500).body("Erro ao adicionar notificação");
        }
    }

}
