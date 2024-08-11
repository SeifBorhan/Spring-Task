package com.spring.task.model.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.*;

@Entity
@Table(name="Course")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    private int credit;

    @ManyToMany(mappedBy = "courses",cascade = CascadeType.ALL)
    private List<Author> authors;

    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings;

    @OneToOne(mappedBy = "course",cascade = CascadeType.ALL, orphanRemoval = true)
    private Assessment assessment;
}
