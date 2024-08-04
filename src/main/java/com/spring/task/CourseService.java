package com.spring.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CourseService {
    private CourseRecommender courseRecommender;

    @Autowired
    @Qualifier("secondCourseRecommender")
    public void setCourseRecommender(CourseRecommender courseRecommender){
        this.courseRecommender = courseRecommender;
    }

    public CourseRecommender getCourseRecommender() {
        return courseRecommender;
    }
}
