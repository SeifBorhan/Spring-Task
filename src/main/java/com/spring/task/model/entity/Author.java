package com.spring.task.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.Mapping;

import java.util.Date;
import java.util.*;

@Entity
@Table(name="Author")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private Date birthdate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "author_course",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;

}
