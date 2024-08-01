package com.spring.task;

import com.spring.task.model.Course;
import com.spring.task.repo.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class CourseService {
    private CourseRecommender courseRecommender;

    private CourseRepo courseRepo;

    @Autowired
    public CourseService(
            @Qualifier("firstCourseRecommender") CourseRecommender courseRecommender) {
        this.courseRecommender = courseRecommender;
    }

    @Autowired
    @Qualifier("secondCourseRecommender")
    public void setCourseRecommender(CourseRecommender courseRecommender){
        this.courseRecommender = courseRecommender;
    }

    private JdbcTemplate template;

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Transactional
    public void createCourse(Course course) {

        String sql="insert into course values(?,?,?,?)";
        template.update(sql,course.getId(),course.getName(),course.getDescription(),course.getCredit());
    }

    @Transactional
    public void updateCourse(Course course){
        String sql="update course set name = ?,description = ?,credit =? where id = ?";
        template.update(sql,course.getName(),course.getDescription(),course.getCredit(),course.getId());
    }

    @Transactional
    public void deleteCourse(Course course){
        String sql="delete from course where id = ?";
        template.update(sql,course.getId());
    }

    @Transactional
    public Course viewCourse(Course course){
        String sql="select * from course where id = ?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Course>(Course.class),course.getId());}

}
