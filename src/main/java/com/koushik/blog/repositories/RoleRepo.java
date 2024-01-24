package com.koushik.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.koushik.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
