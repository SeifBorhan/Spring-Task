package com.spring.task.model.entity;


import jakarta.persistence.*;
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

    @NotNull
    @Column()
    private String name;

    private String description;

    private int credit;

    @ManyToMany(mappedBy = "courses",cascade = CascadeType.ALL)
    private List<Author> authors;

    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings;

    @OneToOne(mappedBy = "course",cascade = CascadeType.ALL, orphanRemoval = true)
    private Assessment assessment;
}
