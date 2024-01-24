package com.koushik.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.koushik.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
