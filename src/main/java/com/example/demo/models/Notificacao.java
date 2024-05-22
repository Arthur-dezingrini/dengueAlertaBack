package com.example.demo.models;

import com.example.demo.DTOs.RegistrarNotificacaoDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notificacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date data;
    private String localizacao;
    private String longitude;
    private String latitude;
    private Integer usuario;
    private Integer foco;
    private String foto;

    public Notificacao(RegistrarNotificacaoDTO data) {
        this.data = data.data();
        this.localizacao = data.localizacao();
        this.longitude = data.longitude();
        this.latitude = data.latitude();
        this.usuario = data.usuario();
        this.foco = data.foco();
        this.foto = data.foto();
    }
}
