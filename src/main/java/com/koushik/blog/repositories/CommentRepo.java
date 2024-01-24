package com.koushik.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.koushik.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{
   
}
