package com.spring.task.controller;

import com.spring.task.CourseRecommender;
import com.spring.task.CourseService;
import com.spring.task.model.Course;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {
    private final Course course;
    private CourseRecommender courseRecommender;
        private CourseService courseService;

    public CourseController(CourseRecommender courseRecommender, CourseService courseService, Course course) {
        this.courseRecommender = courseRecommender;
        this.courseService = courseService;
        this.course = course;
    }

    @GetMapping("/view/{id}")
    public Course viewCourse(@PathVariable int id) {
        return courseService.viewCourse(id);
    }

    @PostMapping("/add")
    public void createCourse(Course course) {
        courseService.createCourse(course);
    }

    @PutMapping("/update/{id}")
    public void updateCourse(@PathVariable int id, Course course) {
        courseService.updateCourse(id,course);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCourse(@PathVariable int id) {
        courseService.deleteCourse(id);
    }

    @GetMapping("/discover")
    public List<Course> discover() {
        return courseRecommender.recommendCourses();
    }

}
