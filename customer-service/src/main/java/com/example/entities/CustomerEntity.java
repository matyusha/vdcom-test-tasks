package com.example.entities;

import com.example.models.Gender;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(schema = "app", name = "customer")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private String mail;
    @Enumerated(EnumType.STRING)
    private Gender gender = Gender.MALE;
    private LocalDateTime createAt;
    private LocalDateTime updateAd;

    @PrePersist
    private void prePersist() {
        createAt = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate() {
        updateAd = LocalDateTime.now();
    }
}
