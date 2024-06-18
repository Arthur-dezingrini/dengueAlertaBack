package com.example.demo.models;

import com.example.demo.DTOs.UsuarioDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario extends Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String senha;

    public Usuario(UsuarioDTO user) {
        super(user.nome(), user.cpf(), user.email(), user.celular());
        this.setNome(user.nome());
        this.setCpf(user.cpf());
        this.setEmail(user.email());
        this.setCelular(user.celular());
        this.senha = user.senha();
    }
}
