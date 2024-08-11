package com.spring.task.model.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Assessment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Assessment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String content;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name= "course_id")
    private Course course;

}
