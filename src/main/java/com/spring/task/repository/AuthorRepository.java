package com.spring.task.repository;

import com.spring.task.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByEmail(String email);
}
