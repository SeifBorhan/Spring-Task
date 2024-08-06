package com.spring.task;

import com.spring.task.model.dto.CourseDTO;
import com.spring.task.model.entity.Course;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
public class FirstCourseRecommender implements CourseRecommender{
    public List<CourseDTO> recommendCourses(){
        System.out.print("FirstCourseRecommender");
        return new ArrayList<CourseDTO>();
    }
}
