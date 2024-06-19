package com.example.demo.services;

import com.example.demo.DTOs.LoginDTO;
import com.example.demo.DTOs.LoginResponseDTO;
import com.example.demo.repositories.usuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class LoginService {

    private usuarioRepository usuarioRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtEncoder jwtEncoder;

    public LoginService(usuarioRepository usuarioRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtEncoder jwtEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    public ResponseEntity<LoginResponseDTO> verificaLogin(LoginDTO loginDTO) {
        var user = usuarioRepository.findByCpf(loginDTO.cpf());
        if (user.isEmpty() || !user.get().isLoginCorrect(loginDTO, bCryptPasswordEncoder)) {
            throw new BadCredentialsException("Usuario ou senha invalidos");
        }

        var now = Instant.now();
        var expiresIn = 300L;

        var claims = JwtClaimsSet.builder()
                .issuer("BackEnd ")
                .subject((user.get().getId()).toString())
                .expiresAt(now.plusSeconds(expiresIn))
                .issuedAt(now)
                .build();


        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponseDTO(jwtValue, expiresIn));
    }
}
