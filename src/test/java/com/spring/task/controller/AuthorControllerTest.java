package com.spring.task.controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import com.spring.task.model.dto.AuthorDTO;
import com.spring.task.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AuthorService authorService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAuthorByEmail_validEmail_succeed() throws Exception {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setEmail("x@gmail.com");
        when(authorService.getAuthorByEmail(authorDTO.getEmail())).thenReturn(authorDTO);

        mockMvc.perform(get("/authors/x@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email")
                        .value("x@gmail.com"));
    }

    @Test
    void getAuthorByEmail_invalidEmail_notFound() throws Exception {

        when(authorService.getAuthorByEmail("DNE"))
                .thenThrow(new RuntimeException("Author not found"));

        mockMvc.perform(get("/authors/DNE"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Author not found"));
    }

}