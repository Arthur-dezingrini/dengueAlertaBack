package com.example.demo.models;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class Pessoa {
    private String nome;
    private Long cpf;
    private String email;
    private Long celular;

    public Pessoa(String nome, Long cpf, String email, Long telefone) {
    }

    public Pessoa() {

    }
}
