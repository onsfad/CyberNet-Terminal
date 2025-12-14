package com.austine.blog.repository;

import com.austine.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRespository extends JpaRepository<Post, Long> {
}
