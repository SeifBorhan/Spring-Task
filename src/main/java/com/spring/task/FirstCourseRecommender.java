package com.spring.task;

import org.springframework.stereotype.Component;

@Component
public class FirstCourseRecommender implements CourseRecommender{
    public int recommendCourse(){
        return 1;
    }
}
