package com.example.demo.services;

import com.example.demo.DTOs.RegistrarNotificacaoDTO;
import com.example.demo.models.Notificacao;
import com.example.demo.repositories.notificacaoRepository;
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
import java.util.UUID;
import java.util.List;

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
                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(imagemBytes));
                    BufferedImage resizedImage = resizeImage(image, 800, 600);
                    byte[] compressedImageBytes = compressImage(resizedImage, 1f);
                    imageUrl = s3Service.uploadFile(compressedImageBytes, UUID.randomUUID() + ".jpg", "image/jpeg");
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

    public ResponseEntity<List> getNotificacoesUsuario() {
        try {
            return ResponseEntity.status(200).body(notificacaoRepository.findAll());
        } catch (Exception e) {
            System.out.println("Erro" + e.getMessage());
        }
        return null;
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

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(resultingImage, 0, 0, null);
        g2d.dispose();
        return outputImage;
    }

    private byte[] compressImage(BufferedImage image, float quality) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(quality);

        writer.write(null, new javax.imageio.IIOImage(image, null, null), param);
        writer.dispose();
        ios.close();
        baos.close();

        return baos.toByteArray();
    }
}
