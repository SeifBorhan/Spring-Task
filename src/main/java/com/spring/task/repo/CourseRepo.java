package com.spring.task.repo;

import com.spring.task.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CourseRepo {
    private JdbcTemplate template;


    public JdbcTemplate getTemplate() {
        return template;
    }
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
