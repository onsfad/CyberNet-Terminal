package com.austine.blog.repository;

import com.austine.blog.model.Pages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageRepository extends JpaRepository<Pages, Long> {

}
