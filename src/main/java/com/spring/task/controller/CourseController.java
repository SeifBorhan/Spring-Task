package com.spring.task.controller;

import com.spring.task.CourseRecommender;
import com.spring.task.model.Course;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {
        private CourseRecommender courseRecommender;

    public CourseController(CourseRecommender courseRecommender) {
        this.courseRecommender = courseRecommender;
    }

    @GetMapping("/view/{id}")
    public Course viewCourse(@PathVariable int id) {
        return null;
    }

    @PostMapping("/add")
    public void createCourse(Course course) {

    }

    @PutMapping("/update/{id}")
    public void updateCourse(@PathVariable int id, Course course) {}

    @DeleteMapping("/delete/{id}")
    public void deleteCourse(@PathVariable int id) {}

    @GetMapping("/discover")
    public List<Course> discover() {
        return courseRecommender.recommendCourses();
    }

}
