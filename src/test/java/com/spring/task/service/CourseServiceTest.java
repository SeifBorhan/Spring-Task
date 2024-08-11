package com.spring.task.service;

import com.spring.task.model.dto.CourseDTO;
import com.spring.task.model.entity.Course;
import com.spring.task.model.mapper.CourseMapper;
import com.spring.task.repository.CourseRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseService courseService;

    private Course course;
    private Course course2;
    private CourseDTO courseDTO;
    private CourseDTO courseDTO2;

    private List<Course> courses;
    private List<CourseDTO> courseDTOs;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        course = Course.builder()
                .id(1L)
                .name("Introduction to Java")
                .description("A comprehensive course on Java programming.")
                .credit(3)
                .build();

        course2 = Course.builder()
                .id(1L)
                .name("Introduction to python")
                .description("A comprehensive course on python.")
                .credit(4)
                .build();

        courseDTO = CourseDTO.builder()
                .id(1L)
                .name("Introduction to Java")
                .description("A comprehensive course on Java programming.")
                .credit(3)
                .build();

        courseDTO2 = CourseDTO.builder()
                .id(2L)
                .name("Introduction to python")
                .description("A comprehensive course on python.")
                .credit(4)
                .build();

        courses = Arrays.asList(course, course2);


        courseDTOs = Arrays.asList(courseDTO, courseDTO2);
    }


    @Test
    void createCourse_validInput_succeed() {


        when(courseMapper.toEntity(courseDTO)).thenReturn(course);
        when(courseRepository.save(course)).thenReturn(course);
        when(courseMapper.toDTO(course)).thenReturn(courseDTO);

        CourseDTO result = courseService.createCourse(courseDTO);

        assertNotNull(result);
        assertEquals(courseDTO.getId(), result.getId());

        verify(courseMapper, times(1)).toEntity(courseDTO);
        verify(courseRepository,times(1)).save(course);
        verify(courseMapper,times(1)).toDTO(course);
    }

    @Test
    void viewCourse_validInput_succeed() {

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseMapper.toDTO(course)).thenReturn(courseDTO);

        CourseDTO result = courseService.viewCourse(courseDTO.getId());

        assertNotNull(result);
        assertEquals(courseDTO.getId(), result.getId());

        verify(courseMapper, times(1)).toDTO(course);
        verify(courseRepository,times(1)).findById(courseDTO.getId());
    }

    @Test
    void viewCourse_invalidInput_idDoesNotExist_fail() {
        long wrongId = 3L;

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            courseService.viewCourse(wrongId);
        });

        assertEquals("Course not found", thrown.getMessage());
    }
    @Test
    void viewCourse_invalidInput_idIsZero_fail() {

        RuntimeException thrown = assertThrows(IllegalArgumentException.class, () -> {
            courseService.viewCourse(0);
        });

        assertEquals("Id cannot be zero", thrown.getMessage());
    }

    @Test
    void deleteCourse_validId_succeed() {

        courseService.deleteCourse(courseDTO.getId());

        verify(courseRepository).deleteById(courseDTO.getId());
    }

    @Test
    void deleteCourse_invalidId_zero_throwsException() {

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            courseService.deleteCourse(0);
        });
        assertEquals("Id cannot be zero", thrown.getMessage());
    }





    @Test
    void updateCourse_validInput_succeed() {
        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        course.setName("New Course Name");
        when(courseRepository.save(course)).thenReturn(course);
        courseDTO.setName("New Course Name");
        when(courseMapper.toDTO(course)).thenReturn(courseDTO);

        CourseDTO updatedCourseDTO = courseService.updateCourse(course.getId(), "New Course Name");

        assertEquals("New Course Name", updatedCourseDTO.getName());
        verify(courseRepository).save(course);
        verify(courseMapper).toDTO(course);
    }

    @Test
    public void testUpdateCourse_invalidId_fail() {
        long wrongID = 4L;

        when(courseRepository.findById(wrongID)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            courseService.updateCourse(wrongID,"New Course Name");
        });

        assertEquals("Course not found", thrown.getMessage());


    }

    @Test
    public void testGetAllCourses() {
        // Arrange
        PageRequest pageRequest = PageRequest.of(0, 10); // Page 0, size 10
        Page<Course> coursePage = new PageImpl<>(courses, pageRequest, courses.size());
        when(courseRepository.findAll(pageRequest)).thenReturn(coursePage);
        when(courseMapper.toDTO(courses.get(0))).thenReturn(courseDTOs.get(0));
        when(courseMapper.toDTO(courses.get(1))).thenReturn(courseDTOs.get(1));

        // Act
        Page<CourseDTO> result = courseService.getAllCourses(0, 10);

        // Assert
        assertEquals(2, result.getTotalElements());
        assertEquals(courseDTOs.get(0), result.getContent().get(0));
        assertEquals(courseDTOs.get(1), result.getContent().get(1));
    }
}