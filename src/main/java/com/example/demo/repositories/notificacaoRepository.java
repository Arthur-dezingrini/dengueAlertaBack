package com.example.demo.repositories;

import com.example.demo.models.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface notificacaoRepository extends JpaRepository<Notificacao, Long> {
}
