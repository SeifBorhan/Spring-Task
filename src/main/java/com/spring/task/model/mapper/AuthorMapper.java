package com.spring.task.model.mapper;

import com.spring.task.model.dto.AuthorDTO;
import com.spring.task.model.entity.Author;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDTO toDTO(Author author);
    Author toEntity(AuthorDTO authorDTO);
}
