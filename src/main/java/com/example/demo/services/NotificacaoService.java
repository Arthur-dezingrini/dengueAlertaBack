package com.example.demo.services;

import com.example.demo.DTOs.RegistrarNotificacaoDTO;
import com.example.demo.models.Notificacao;
import com.example.demo.repositories.notificacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.util.Base64;
import java.util.UUID;

@Service
public class NotificacaoService {

    @Autowired
    notificacaoRepository notificacaoRepository;
    @Autowired
    S3Service s3Service;

    public ResponseEntity<String> postNotificacao(RegistrarNotificacaoDTO data) {
        try {
            if (data != null) {
                String imageUrl = "";
                if (data.imagem() != null) {
                    byte[] imagemBytes = Base64.getDecoder().decode(data.imagem());
                    imageUrl = s3Service.uploadFile(imagemBytes, UUID.randomUUID() + ".jpg", "image/jpeg");
                }
                Notificacao notificacao = new Notificacao(data.data(), data.endereco(), data.bairro(), data.cidade(), data.descricao(), data.denunciaAnonima(), imageUrl);
                notificacaoRepository.save(notificacao);
                return ResponseEntity.status(200).body("Notificação adicionada com sucesso!");
            } else {
                return ResponseEntity.badRequest().body("Dados da notificação são inválidos");
            }
        } catch (Exception e) {
            System.out.println("Erro" + e.getMessage());
        }
        return ResponseEntity.status(500).body("Erro ao adicionar notificação");
    }

    public void getNotificacao(Long id, Long userId) {
        try {
            if (id != null) {
                notificacaoRepository.getReferenceById(id);
            }
        } catch (Exception e) {
            System.out.println("Erro" + e.getMessage());
        }
    }

    public void getNotificacoesUsuario(Long id) {
        try {
            if (id != null) {
                notificacaoRepository.getReferenceById(id);
            }
        } catch (Exception e) {
            System.out.println("Erro" + e.getMessage());
        }
    }

    public void putNotificacao(Notificacao notificacao, Long id) {
        try {
            if (id != null) {
                Notificacao notificacaoAntiga = notificacaoRepository.getReferenceById(id);
                notificacaoRepository.save(notificacao);
            }
        } catch (Exception e) {
            System.out.println("Erro" + e.getMessage());
        }
    }

    public void deleteNotificacao(Long id) {
        try {
            if (id != null) {
                Notificacao notificacao = notificacaoRepository.getReferenceById(id);
                notificacaoRepository.delete(notificacao);
            }
        } catch (Exception e) {
            System.out.println("Erro" + e.getMessage());
        }
    }
}
