package com.spring.task.model.mapper;

import com.spring.task.model.dto.CourseDTO;
import com.spring.task.model.entity.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    Course toEntity(CourseDTO courseDTO);
    CourseDTO toDTO(Course course);
}
