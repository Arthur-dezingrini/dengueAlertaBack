package com.example.demo.DTOs;

import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

public record RegistrarNotificacaoDTO(
        Date data,
        String endereco,
        String bairro,
        String cidade,
        String descricao,
        boolean denunciaAnonima,
        String imagem,
        Long userId,
        double latitude,
        double longitude) {
}
