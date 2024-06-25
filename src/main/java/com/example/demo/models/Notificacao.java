package com.example.demo.models;

import com.example.demo.DTOs.RegistrarNotificacaoDTO;
import com.fasterxml.jackson.databind.util.BeanUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.sql.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table()
public class Notificacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date data;
    private String endereco;
    private String bairro;
    private String cidade;
    private String descricao;
    private boolean denunciaAnonima;
    private String imageUrl;
    private Long userId;
    private Double latitude;
    private Double longitude;

    public Notificacao(Date data, String endereco, String bairro , String cidade, String descricao, boolean denunciaAnonima, String imageUrl, Long userId, double latitude, double longitude) {
        this.data = data;
        this.endereco = endereco;
        this.bairro = bairro;
        this.cidade = cidade;
        this.descricao = descricao;
        this.denunciaAnonima = denunciaAnonima;
        this.imageUrl = imageUrl;
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Notificacao(RegistrarNotificacaoDTO data) {
        BeanUtils.copyProperties(data, this);
    }
}
