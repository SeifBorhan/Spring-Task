package com.spring.task;

import com.spring.task.model.Course;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.RouteMatcher;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
public class FirstCourseRecommender implements CourseRecommender{
    public List<Course> recommendCourses(){
        System.out.print("FirstCourseRecommender");
        return new ArrayList<Course>();
    }
}
