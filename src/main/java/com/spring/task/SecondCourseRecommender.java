package com.spring.task;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


public class SecondCourseRecommender implements CourseRecommender{
    public List<String> recommendCourses(){
        System.out.print("SecondCourseRecommender");
        return new ArrayList<String>();
    }
}
