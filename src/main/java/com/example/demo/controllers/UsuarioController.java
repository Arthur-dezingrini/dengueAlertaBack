package com.example.demo.controllers;

import com.example.demo.DTOs.RegistrarNotificacaoDTO;
import com.example.demo.models.Notificacao;
import com.example.demo.services.NotificacaoService;
import jakarta.websocket.server.PathParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsuarioController {

    NotificacaoService notificacaoService;

    @PostMapping("/notificar-foco")
    public void registraNorificacao (@RequestBody RegistrarNotificacaoDTO data) {
        if(data != null) {
            notificacaoService.postNotificacao(new Notificacao(data));
        }
    }

    @GetMapping("/busca-notificacao")
    public void buscaNotificacao (@RequestParam Long id, Long userId) {
        if (id != null && userId != null) {
            notificacaoService.getNotificacao(id, userId);
        }
    }

    @GetMapping("/notificacoes")
    public void buscaNotificacoes (@RequestParam Long userId) {
        if (userId != null) {
            notificacaoService.getNotificacoesUsuario(userId);
        }
    }

    @PutMapping("/editar-notificacao/{ID}")
    public void editarNotificacao (@RequestBody RegistrarNotificacaoDTO data, @PathParam("ID") Long id) {
        if (data != null) {
            notificacaoService.putNotificacao(new Notificacao(data), id);
        }
    }

    @DeleteMapping("/deletar-notificacao/{ID}")
    public void deletarNotificacao (@PathParam("ID") Long id) {
        if (id != null) {
            notificacaoService.deleteNotificacao(id);
        }
    }
}