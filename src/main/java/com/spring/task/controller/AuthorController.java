package com.spring.task.controller;

import com.spring.task.model.dto.AuthorDTO;
import com.spring.task.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }


    @GetMapping("/{email}")
    public AuthorDTO getAuthorByEmail(@PathVariable String email) {
        return authorService.getAuthorByEmail(email);
    }
}
