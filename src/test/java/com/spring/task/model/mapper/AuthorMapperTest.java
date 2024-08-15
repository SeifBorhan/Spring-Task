package com.spring.task.model.mapper;

import com.spring.task.model.dto.AuthorDTO;
import com.spring.task.model.entity.Author;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.mapstruct.factory.Mappers;


class AuthorMapperTest {

    private final AuthorMapper authorMapper = Mappers.getMapper(AuthorMapper.class);

    @Test
    public void testToDTO() {
        Author author = new Author();
        author.setEmail("test@example.com");
        author.setName("John Doe");


        AuthorDTO authorDTO = authorMapper.toDTO(author);

        assertEquals("test@example.com", authorDTO.getEmail());
        assertEquals("John Doe", authorDTO.getName());
    }

    @Test
    public void testToEntity() {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setEmail("test@example.com");
        authorDTO.setName("John Doe");

        Author author = authorMapper.toEntity(authorDTO);

        assertEquals("test@example.com", author.getEmail());
        assertEquals("John Doe", author.getName());
    }

    @Test
    public void testToDTOWithNull() {
        AuthorDTO authorDTO = authorMapper.toDTO(null);
        assertNull(authorDTO, "Expected null when input is null");
    }

    @Test
    public void testToEntityWithNull() {
        Author author = authorMapper.toEntity(null);
        assertNull(author, "Expected null when input is null");
    }

}
