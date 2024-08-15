package com.spring.task.model.mapper;


import com.spring.task.model.dto.CourseDTO;
import com.spring.task.model.entity.Course;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CourseMapperTest {

    private final CourseMapper courseMapper = Mappers.getMapper(CourseMapper.class);

    @Test
    public void testToDTO() {
        Course course = new Course();
        course.setId(1L);
        course.setName("Java");
        course.setDescription("Java Programming Course");
        course.setCredit(5);

        CourseDTO courseDTO = courseMapper.toDTO(course);

        assertEquals(1L, courseDTO.getId());
        assertEquals("Java", courseDTO.getName());
        assertEquals("Java Programming Course", courseDTO.getDescription());
        assertEquals(5, courseDTO.getCredit());
    }

    @Test
    public void testToEntity() {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(1L);
        courseDTO.setName("Java");
        courseDTO.setDescription("Java Programming Course");
        courseDTO.setCredit(5);

        Course course = courseMapper.toEntity(courseDTO);

        assertEquals(1L, course.getId());
        assertEquals("Java", course.getName());
        assertEquals("Java Programming Course", course.getDescription());
        assertEquals(5, course.getCredit());
    }

    @Test
    public void testToDTOWithNull() {
        CourseDTO courseDTO = courseMapper.toDTO(null);
        assertNull(courseDTO, "Expected null when input is null");
    }

    @Test
    public void testToEntityWithNull() {
        Course course = courseMapper.toEntity(null);
        assertNull(course, "Expected null when input is null");
    }
}