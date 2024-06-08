package com.example.demo.models;

import com.example.demo.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.NonNull;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Foco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
