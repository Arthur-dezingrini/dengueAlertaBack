package com.example.demo.controllers;

import com.example.demo.DTOs.RegistrarNotificacaoDTO;
import com.example.demo.models.Notificacao;
import com.example.demo.services.NotificacaoService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/foco")
@RestController
public class NotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    @PostMapping("/notificar")
    public ResponseEntity<String> registraNorificacao (@RequestBody RegistrarNotificacaoDTO data) {
        if(data != null) {
            ResponseEntity<String> not = notificacaoService.postNotificacao(data);
            return not;
        } else {
            return null;
        }
    }

    @GetMapping("/busca-notificacao")
    public void buscaNotificacao (@RequestParam Long id, Long userId) {
        if (id != null && userId != null) {
            notificacaoService.getNotificacao(id, userId);
        }
    }

    @GetMapping("/notificacoes")
    public ResponseEntity buscaNotificacoes(@RequestParam(name = "id") Long userId) {
        return notificacaoService.getNotificacoesUsuario(userId);
    }

    @PutMapping("/atualizar")
    public void editarNotificacao (@RequestBody RegistrarNotificacaoDTO data, @PathParam("id") Long id) {
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
