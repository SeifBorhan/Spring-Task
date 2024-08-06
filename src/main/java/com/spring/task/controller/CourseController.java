package com.spring.task.controller;

import com.spring.task.CourseRecommender;
import com.spring.task.model.dto.CourseDTO;
import com.spring.task.service.CourseService;
import com.spring.task.model.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {

    private final CourseService courseService;
    private final CourseRecommender courseRecommender;

    @Autowired
    public CourseController(CourseService courseService, CourseRecommender courseRecommender) {
        this.courseService = courseService;
        this.courseRecommender = courseRecommender;
    }

    @GetMapping("/view/{id}")
    public CourseDTO viewCourse(@PathVariable long id) {
        return courseService.viewCourse(id);
    }

    @PostMapping("/add")
    public CourseDTO createCourse(Course course) {
        return courseService.createCourse(course);
    }

    @PutMapping("/update/{id}")
    public CourseDTO updateCourse(@PathVariable long id, String name) {
        return courseService.updateCourse(id,"Nemo");
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCourse(@PathVariable long id) {
        courseService.deleteCourse(id);
    }

    @GetMapping("/discover")
    public List<CourseDTO> discover() {
        return courseRecommender.recommendCourses();
    }

}
