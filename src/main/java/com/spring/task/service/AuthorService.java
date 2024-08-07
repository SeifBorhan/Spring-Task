package com.spring.task.service;


import com.spring.task.model.dto.AuthorDTO;
import com.spring.task.model.entity.Author;
import com.spring.task.model.mapper.AuthorMapper;
import com.spring.task.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    public AuthorDTO getAuthorByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email must not be null or empty");
        }
        Optional<Author> author = Optional.ofNullable(
                Optional.ofNullable(
                        authorRepository.findByEmail(email))
                .orElseThrow(
                        () -> new RuntimeException("Author not found")));

        return author.map(authorMapper::toDTO).orElse(null);
    }
}
