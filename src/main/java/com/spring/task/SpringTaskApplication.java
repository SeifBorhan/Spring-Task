package com.spring.task;

import com.spring.task.model.Course;
import com.spring.task.repo.CourseRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringTaskApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringTaskApplication.class, args);
        CourseService service = context.getBean(CourseService.class);

        Course c1 = context.getBean(Course.class);
        c1.setId(2);
        c1.setName("CS1");
        c1.setDescription("EASY");
        c1.setCredit(8);

        service.createCourse(c1);


    }

}
