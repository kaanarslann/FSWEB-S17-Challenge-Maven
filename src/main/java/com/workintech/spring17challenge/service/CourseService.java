package com.workintech.spring17challenge.service;

import com.workintech.spring17challenge.entity.Course;
import com.workintech.spring17challenge.entity.HighCourseGpa;
import com.workintech.spring17challenge.entity.LowCourseGpa;
import com.workintech.spring17challenge.entity.MediumCourseGpa;
import com.workintech.spring17challenge.exceptions.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    private LowCourseGpa lowCourseGpa;
    private MediumCourseGpa mediumCourseGpa;
    private HighCourseGpa highCourseGpa;

    public void validateCourse(Course course) {
        if (course.getName() == null || course.getName().trim().isEmpty()) {
            throw new ApiException("Course name cannot be null or blank", HttpStatus.BAD_REQUEST);
        }

        if (course.getCredit() < 1 || course.getCredit() > 4) {
            throw new ApiException("Credit must be between 1 and 4", HttpStatus.BAD_REQUEST);
        }

        if (course.getGrade() == null) {
            throw new ApiException("Grade cannot be null", HttpStatus.BAD_REQUEST);
        }
    }
}
