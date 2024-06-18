package com.example.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Agente extends Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String areaAtuacao;

    public Agente(String nome, Long cpf, String email, Long telefone) {
        super(nome, cpf, email, telefone);
    }

    public Agente() {
        super();
    }
}
