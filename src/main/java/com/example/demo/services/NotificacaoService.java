package com.example.demo.services;

import com.example.demo.DTOs.RegistrarNotificacaoDTO;
import com.example.demo.models.Notificacao;
import com.example.demo.repositories.notificacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.UUID;
import java.util.List;

@Service
public class NotificacaoService {

    @Autowired
    notificacaoRepository notificacaoRepository;
    @Autowired
    S3Service s3Service;
    @Autowired
    ImageService imageService;

    public ResponseEntity<String> postNotificacao(RegistrarNotificacaoDTO data) {
        try {
            if (data != null) {
               ; String imageUrl = "";
                if (data.imagem() != null) {
                    byte[] imagemBytes = Base64.getDecoder().decode(data.imagem());
                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(imagemBytes));
                    BufferedImage resizedImage = imageService.resizeImage(image, 800, 600);
                    byte[] compressedImageBytes = imageService.compressImage(resizedImage, 1f);
                    imageUrl = s3Service.uploadFile(compressedImageBytes, UUID.randomUUID() + ".jpg", "image/jpeg");
                }
                Notificacao notificacao = new Notificacao(data.data(), data.endereco(), data.bairro(), data.cidade(), data.descricao(), data.denunciaAnonima(), imageUrl, data.userId(), data.latitude(), data.longitude());
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

    public ResponseEntity<List> getNotificacoesUsuario(Long id) {
        try {
            return ResponseEntity.status(200).body(notificacaoRepository.findByUserId(id));
        } catch (Exception e) {
            System.out.println("Erro" + e.getMessage());
        }
        return null;
    }

    public void putNotificacao(Notificacao notificacao, Long id) {
        try {
            if (id != null) {
                notificacao.setId(id);
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
