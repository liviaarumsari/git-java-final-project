package org.example.javafinalproject.repository;

import org.example.javafinalproject.enums.ERole;
import org.example.javafinalproject.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(ERole role);
}
