package com.spring.task;

import com.spring.task.model.Course;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SecondCourseRecommender implements CourseRecommender{
    public List<Course> recommendCourses(){
        System.out.print("SecondCourseRecommender");
        return new ArrayList<Course>();
    }
}
