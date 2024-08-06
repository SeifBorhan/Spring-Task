package com.spring.task;
import com.spring.task.model.dto.CourseDTO;
import com.spring.task.model.entity.Course;

import java.util.List;
public interface CourseRecommender {
    List<CourseDTO> recommendCourses();
}
