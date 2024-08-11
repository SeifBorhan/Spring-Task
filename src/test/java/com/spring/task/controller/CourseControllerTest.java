package com.spring.task.controller;

import static org.mockito.Mockito.verify;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.task.CourseRecommender;
import com.spring.task.model.dto.CourseDTO;
import com.spring.task.model.entity.Course;
import com.spring.task.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(CourseController.class)
@ExtendWith(MockitoExtension.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @MockBean
    private CourseRecommender courseRecommender;

    private Course course;
    private CourseDTO courseDTO;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        course = Course.builder()
                .id(1L)
                .name("Introduction to Java")
                .description("A comprehensive course on Java programming.")
                .credit(3)
                .build();

        courseDTO = CourseDTO.builder()
                .id(1L)
                .name("Introduction to Java")
                .description("A comprehensive course on Java programming.")
                .credit(3)
                .build();
    }

    @Test
    void viewCourse_validInput_succeed() throws Exception {
        when(courseService.viewCourse(courseDTO.getId())).thenReturn(courseDTO);

        mockMvc.perform(get("/courses/view/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(courseDTO.getId()));
    }

    @Test
    void createCourse() throws Exception {
        when(courseService.createCourse(courseDTO)).thenReturn(courseDTO);

        String courseDTOJson = objectMapper.writeValueAsString(courseDTO);

        // Act & Assert
        mockMvc.perform(post("/courses/add")
                        .content(courseDTOJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(courseDTO.getId()));

    }

    @Test
    void updateCourse() throws Exception{

        when(courseService.updateCourse(courseDTO.getId(),"Nemo")).thenReturn(courseDTO);
        courseDTO.setName("Nemo");


        mockMvc.perform(put("/courses/update/1", courseDTO.getId())
                        .content("Nemo")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(courseDTO.getId()))
                .andExpect(jsonPath("$.name").value("Nemo"));
    }

    @Test
    void deleteCourse() throws Exception {

        mockMvc.perform(delete("/courses/delete/{id}", courseDTO.getId()))
                .andExpect(status().isOk());

        verify(courseService).deleteCourse(courseDTO.getId());
    }

    @Test
    void discover() throws Exception {
        CourseDTO course1 = new CourseDTO(1L, "Course 1", "Description 1", 3);
        CourseDTO course2 = new CourseDTO(2L, "Course 2", "Description 2", 4);
        List<CourseDTO> recommendedCourses = Arrays.asList(course1, course2);

        when(courseRecommender.recommendCourses()).thenReturn(recommendedCourses);

        mockMvc.perform(get("/courses/discover"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(course1.getId()))
                .andExpect(jsonPath("$[1].id").value(course2.getId()));
    }

    @Test
    void getAllCourses() throws Exception {

        CourseDTO course1 = new CourseDTO(1L, "Course 1", "Description 1", 3);
        CourseDTO course2 = new CourseDTO(2L, "Course 2", "Description 2", 4);
        List<CourseDTO> courses = Arrays.asList(course1, course2);
        Page<CourseDTO> coursePage = new PageImpl<>(courses, PageRequest.of(0, 2), courses.size());

        when(courseService.getAllCourses(0, 2)).thenReturn(coursePage);


        mockMvc.perform(get("/courses/allCourses")
                        .param("page", "0")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(course1.getId()))
                .andExpect(jsonPath("$.content[1].id").value(course2.getId()));
    }
}