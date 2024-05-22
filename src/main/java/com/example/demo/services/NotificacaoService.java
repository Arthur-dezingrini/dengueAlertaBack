package com.example.demo.services;

import com.example.demo.models.Notificacao;
import com.example.demo.repositories.notificacaoRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoService {
    notificacaoRepository notificacaoRepository;

    public void postNotificacao(Notificacao notificacao) {
        try {
            if (notificacao != null) {
                notificacaoRepository.save(notificacao);
            }
        } catch (Exception e) {
            System.out.println("Erro" + e.getMessage());
        }
    }

    public void getNotificacao(Long id, Long userId) {
        try {
            if (id != null) {
                notificacaoRepository.getReferenceById(id);
            }
        } catch (Exception e) {
            System.out.println("Erro" + e.getMessage());
        }
    }

    public void getNotificacoesUsuario(Long id) {
        try {
            if (id != null) {
                notificacaoRepository.getReferenceById(id);
            }
        } catch (Exception e) {
            System.out.println("Erro" + e.getMessage());
        }
    }

    public void putNotificacao(Notificacao notificacao, Long id) {
        try {
            if (id != null) {
                Notificacao notificacaoAntiga = notificacaoRepository.getReferenceById(id);
                notificacaoRepository.save(notificacao);
            }
        } catch (Exception e) {
            System.out.println("Erro" + e.getMessage());
        }
    }

    public void deleteNotificacao(Long id) {
        try {
            if (id != null) {
                Notificacao notificacao = notificacaoRepository.getReferenceById(id);
                notificacaoRepository.delete(notificacao);
            }
        } catch (Exception e) {
            System.out.println("Erro" + e.getMessage());
        }
    }
}
