package com.spring.task.model.dto;

import com.spring.task.model.entity.Assessment;
import com.spring.task.model.entity.Author;
import com.spring.task.model.entity.Rating;
import lombok.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private long id;
    private String name;
    private String description;
    private int credit;
    private List<Author> authors;
    private List<Rating> ratings;
    private Assessment assessment;

}
