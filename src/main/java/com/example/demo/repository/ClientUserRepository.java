package com.example.demo.repository;

import com.example.demo.model.ClientUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientUserRepository extends JpaRepository<ClientUser, Long> {
    Optional<ClientUser> findByEmail(String email); // Método para buscar usuario por email
}