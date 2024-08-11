package com.spring.task.service;

import com.spring.task.CourseRecommender;
import com.spring.task.model.dto.CourseDTO;
import com.spring.task.model.entity.Course;
import com.spring.task.model.mapper.CourseMapper;
import com.spring.task.repository.CourseRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
public class CourseService {
    private CourseRecommender courseRecommender;

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;


    @Autowired
    public CourseService(
            @Qualifier("firstCourseRecommender") CourseRecommender courseRecommender,CourseRepository courseRepository,CourseMapper courseMapper) {
        this.courseRecommender = courseRecommender;
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    @Autowired
    @Qualifier("secondCourseRecommender")
    public void setCourseRecommender(CourseRecommender courseRecommender){
        this.courseRecommender = courseRecommender;
    }


    @Transactional
    public CourseDTO createCourse(@Valid CourseDTO courseDTO) {
        return this.courseMapper.toDTO(courseRepository.save(courseMapper.toEntity(courseDTO)));
    }

    @Transactional
    public CourseDTO updateCourse(long id, String name){
       Optional<Course> optionalCourse = courseRepository.findById(id);
       if(optionalCourse.isPresent()){
           Course c = optionalCourse.get();
           c.setName(name);
           return this.courseMapper.toDTO(courseRepository.save(c));
       }
        throw new RuntimeException("Course not found");
    }



    @Transactional
    public void deleteCourse(long id){
        if (id == 0) {
            throw new IllegalArgumentException("Id cannot be zero");
        }
        courseRepository.deleteById(id);
    }


    @Transactional
    public CourseDTO viewCourse(long id){
        if (id == 0) {
            throw new IllegalArgumentException("Id cannot be zero");
        }

        return courseRepository.findById(id).map(courseMapper::toDTO)
                .orElseThrow(
                () -> new RuntimeException("Course not found"));
    }

    public Page<CourseDTO> getAllCourses(int page, int size) {
        return courseRepository.findAll(PageRequest.of(page, size)).map(courseMapper::toDTO);
    }

}
