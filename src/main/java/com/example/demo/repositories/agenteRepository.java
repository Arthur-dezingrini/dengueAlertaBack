package com.example.demo.repositories;

import com.example.demo.models.Agente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface agenteRepository extends JpaRepository<Agente, Long> {
}
