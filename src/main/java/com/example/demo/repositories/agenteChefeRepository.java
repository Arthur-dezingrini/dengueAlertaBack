package com.example.demo.repositories;

import com.example.demo.models.AgenteChefe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface agenteChefeRepository extends JpaRepository<AgenteChefe, Long> {
}
