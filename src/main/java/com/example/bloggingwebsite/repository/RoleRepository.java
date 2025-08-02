package com.example.bloggingwebsite.repository;

import com.example.bloggingwebsite.models.Role;
import com.example.bloggingwebsite.models.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}