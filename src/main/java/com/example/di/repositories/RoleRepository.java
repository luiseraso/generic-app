package com.example.di.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.di.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
