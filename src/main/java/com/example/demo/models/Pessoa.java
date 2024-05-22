package com.example.demo.models;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class Pessoa {
    @NonNull
    private String nome;
    @NonNull
    private Integer cpf;
    @NonNull
    private String email;
    @NonNull
    private String telefone;


    public void notificaFoco() {

    }
}
