package com.users.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.users.entities.ERole;
import com.users.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Role findByName(String name);

 
}