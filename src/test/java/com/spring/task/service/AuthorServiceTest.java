package com.spring.task.service;

import com.spring.task.model.dto.AuthorDTO;
import com.spring.task.model.entity.Author;
import com.spring.task.model.mapper.AuthorMapper;
import com.spring.task.repository.AuthorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAuthorByEmail_validEmail_succeed() {
        Author author = new Author();
        author.setEmail("x@gmail.com");
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setEmail("x@gmail.com");

        when(authorRepository.findByEmail(author.getEmail())).thenReturn(author);
        when(authorMapper.toDTO(author)).thenReturn(authorDTO);

        AuthorDTO result = authorService.getAuthorByEmail(authorDTO.getEmail());

        assertNotNull(result);
        assertEquals(authorDTO.getEmail(), result.getEmail());

        verify(authorRepository,times(1)).findByEmail(author.getEmail());
        verify(authorMapper,times(1)).toDTO(author);

    }

    @Test
    void getAuthorByEmail_inValidEmail_throwsRuntimeException() {

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            authorService.getAuthorByEmail("DNE");
        });

        assertEquals("Author not found", thrown.getMessage());

    }

    @Test
    void getAuthorByEmail_nullEmail_throwsIllegalArgumentException() {

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            authorService.getAuthorByEmail(null);
        });

        assertEquals("Email must not be null or empty", thrown.getMessage());
    }

    @Test
    void getAuthorByEmail_emptyEmail_throwsIllegalArgumentException() {

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            authorService.getAuthorByEmail("");
        });

        assertEquals("Email must not be null or empty", thrown.getMessage());
    }



}