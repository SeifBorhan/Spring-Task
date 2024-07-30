package com.spring.task;

import org.springframework.stereotype.Component;

@Component
public class SecondCourseRecommender implements CourseRecommender{
    public int recommendCourse(){
        return 2;
    }
}
