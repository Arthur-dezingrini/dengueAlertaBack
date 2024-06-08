package com.example.demo.repositories;

import com.example.demo.models.Foco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface focoRepository extends JpaRepository<Foco, Long> {
}
