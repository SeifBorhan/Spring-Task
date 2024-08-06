package com.spring.task;

import com.spring.task.model.dto.CourseDTO;
import com.spring.task.model.entity.Course;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SecondCourseRecommender implements CourseRecommender{
    public List<CourseDTO> recommendCourses(){
        System.out.print("SecondCourseRecommender");
        return new ArrayList<CourseDTO>();
    }
}
