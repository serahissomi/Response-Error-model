package com.goorm.task.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class Student {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Integer grade;

    @Builder
    public Student(String name, Integer grade) {
        this.name = name;
        this.grade = grade;
    }
}
