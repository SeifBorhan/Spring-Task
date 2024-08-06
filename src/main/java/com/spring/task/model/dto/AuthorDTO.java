package com.spring.task.model.dto;

import com.spring.task.model.entity.Course;
import lombok.*;
import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {
    private Long id;
    private String name;
    private String email;
    private Date birthdate;
    private List<Course> courses;
}
