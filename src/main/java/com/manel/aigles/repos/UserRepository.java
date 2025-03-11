package com.manel.aigles.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manel.aigles.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // Méthodes de recherche personnalisées, si nécessaire
}