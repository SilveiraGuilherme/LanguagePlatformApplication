
package com.project.languageplatform.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.languageplatform.entity.User;

// Repository interface for accessing User entities from the database.

public interface UserRepository extends JpaRepository<User, Long> {
    // Retrieves a user by their email address.
    Optional<User> findByEmail(String email);
}