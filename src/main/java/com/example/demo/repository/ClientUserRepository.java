package com.example.demo.repository;

import com.example.demo.model.clientUser.ClientUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientUserRepository extends JpaRepository<ClientUser, UUID> {
    Optional<ClientUser> findByEmail(String email); // Método para buscar usuario por email
}