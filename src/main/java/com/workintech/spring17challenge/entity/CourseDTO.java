package com.workintech.spring17challenge.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CourseDTO {
    private Course course;
    private double totalGpa;
}
