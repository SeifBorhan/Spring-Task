package com.spring.task.service;

import com.spring.task.CourseRecommender;
import com.spring.task.model.dto.CourseDTO;
import com.spring.task.model.entity.Course;
import com.spring.task.model.mapper.CourseMapper;
import com.spring.task.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CourseService {
    private CourseRecommender courseRecommender;

    private final CourseRepository courseRepository;

    @Autowired
    private CourseMapper courseMapper;


    @Autowired
    public CourseService(
            @Qualifier("firstCourseRecommender") CourseRecommender courseRecommender,CourseRepository courseRepository) {
        this.courseRecommender = courseRecommender;
        this.courseRepository = courseRepository;
    }

    @Autowired
    @Qualifier("secondCourseRecommender")
    public void setCourseRecommender(CourseRecommender courseRecommender){
        this.courseRecommender = courseRecommender;
    }



    @Transactional
    public CourseDTO createCourse(Course course) {
        return this.courseMapper.toDTO(courseRepository.save(course));
       }

    @Transactional
    public CourseDTO updateCourse(long id, String name){
       Optional<Course> optionalCourse = courseRepository.findById(id);
       if(optionalCourse.isPresent()){
           Course c = optionalCourse.get();
           c.setName(name);
           return this.courseMapper.toDTO(courseRepository.save(c));
       }
        return null;
    }



    @Transactional
    public void deleteCourse(long id){
        courseRepository.deleteById(id);
    }


    @Transactional
    public CourseDTO viewCourse(long id){
        Optional<Course> optionalCourse = courseRepository.findById(id);
        return optionalCourse.map(this.courseMapper::toDTO).orElse(null);
    }

    public Page<CourseDTO> getAllCourses(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return courseRepository.findAll(pageRequest).map(courseMapper::toDTO);
    }

}
