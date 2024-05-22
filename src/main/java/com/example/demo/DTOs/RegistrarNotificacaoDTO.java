package com.example.demo.DTOs;

import java.sql.Date;

public record RegistrarNotificacaoDTO(Date data, Integer usuario, String localizacao, Integer foco, String longitude, String latitude, String foto) {
}
