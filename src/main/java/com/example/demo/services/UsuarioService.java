package com.example.demo.services;

import com.example.demo.DTOs.*;
import com.example.demo.models.Notificacao;
import com.example.demo.models.Usuario;
import com.example.demo.repositories.notificacaoRepository;
import com.example.demo.repositories.usuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    ImageService imageService;
    @Autowired
    S3Service s3Service;
    @Autowired
    usuarioRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtEncoder jwtEncoder;

    public UsuarioService(BCryptPasswordEncoder bCryptPasswordEncoder, JwtEncoder jwtEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    public ResponseEntity<String> postUsuario(UsuarioDTO user) {
        try {
            var existUser =  repository.findByCpf(user.cpf());
            if (existUser.isPresent()) {
                return ResponseEntity.status(200).body("Usuario com CPF já Cadastrado");
            }

            Usuario newUser =  new Usuario();
            newUser.setCelular(user.celular());
            newUser.setEmail(user.email());
            newUser.setCpf(user.cpf());
            newUser.setNome(user.nome());
            newUser.setSenha(bCryptPasswordEncoder.encode(user.senha()));

            repository.save(newUser);

            return ResponseEntity.status(201).body("Usuario cadastrado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao adicionar notificação");
        }
    }

    public ResponseEntity<LoginResponseDTO> verificaLogin(LoginDTO loginDTO) throws Exception {
        try {
            var user = repository.findByCpf(loginDTO.cpf());
            if (user.isEmpty() || !user.get().isLoginCorrect(loginDTO, bCryptPasswordEncoder)) {
                return ResponseEntity.status(210).body(new LoginResponseDTO("Usuario ou senha Incoreto, tente novamente", 0L, ""));
            }

            var now = Instant.now();
            var expiresIn = 3200L;

            var claims = JwtClaimsSet.builder()
                    .issuer("BackEnd ")
                    .subject((user.get().getId()).toString())
                    .expiresAt(now.plusSeconds(expiresIn))
                    .issuedAt(now)
                    .build();

            var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

            return ResponseEntity.ok(new LoginResponseDTO(jwtValue, expiresIn, user));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    public String alterarFotoPerfil(AlterarFotoDTO data) throws IOException {
        var user = repository.getReferenceById(data.id());
        System.out.println("chegou aqui");
        System.out.println(user);
        byte[] imagemBytes = Base64.getDecoder().decode(data.foto());
        System.out.println("imagem byte: " + imagemBytes.length);
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imagemBytes));
        System.out.println(image);
        BufferedImage resizedImage = imageService.resizeImage(image, 800, 600);
        byte[] compressedImageBytes = imageService.compressImage(resizedImage, 1f);
        System.out.println(compressedImageBytes.length);
        String imageUrl = s3Service.uploadFile(compressedImageBytes, UUID.randomUUID() + ".jpg", "image/jpeg");
        System.out.println(imageUrl);
        user.setFoto(imageUrl);
        repository.save(user);
        return imageUrl;
    }
}
