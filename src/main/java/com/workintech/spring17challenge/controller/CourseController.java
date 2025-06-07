package com.workintech.spring17challenge.controller;

import com.workintech.spring17challenge.entity.*;
import com.workintech.spring17challenge.exceptions.ApiException;
import com.workintech.spring17challenge.service.CourseService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courses")
public class CourseController {
    public List<Course> courses;
    private CourseService courseService;
    private LowCourseGpa lowCourseGpa;
    private MediumCourseGpa mediumCourseGpa;
    private HighCourseGpa highCourseGpa;

    @PostConstruct
    public void init() {
        this.courses = new ArrayList<>();
    }

    @Autowired
    public CourseController(LowCourseGpa lowCourseGpa, MediumCourseGpa mediumCourseGpa, HighCourseGpa highCourseGpa, CourseService courseService) {
        this.lowCourseGpa = lowCourseGpa;
        this.mediumCourseGpa = mediumCourseGpa;
        this.highCourseGpa = highCourseGpa;
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> getAll() {
        return courses.stream().toList();
    }

    @GetMapping("/{name}")
    public Course getCourse(@PathVariable("name") String name) {
        return courses.stream().filter(course -> course.getName().equals(name)).findFirst().orElseThrow(() -> new ApiException("Course not found " + name, HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addCourse(@RequestBody Course course) {
        courseService.validateCourse(course);

        if (courses.contains(course)) {
            throw new ApiException("Cannot add the same course", HttpStatus.CONFLICT);
        }

        this.courses.add(course);
        double coefficient = course.getGrade().getCoefficient();
        int credit = course.getCredit();
        int baseGpa;

        if (credit <= 2) {
            baseGpa = lowCourseGpa.getGpa();;
        } else if (credit == 3) {
            baseGpa = mediumCourseGpa.getGpa();;
        } else if (credit == 4) {
            baseGpa = highCourseGpa.getGpa();;
        } else {
            baseGpa = 0;
        }

        double totalGpa = coefficient * credit * baseGpa;
        Map<String, Object> response = new HashMap<>();
        response.put("course", course);
        response.put("totalGpa", totalGpa);
        //return new CourseDTO(course, totalGpa);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public CourseDTO updateCourse(@PathVariable("id") int id, @RequestBody Course newCourse) {
        courseService.validateCourse(newCourse);
        this.courses.set(id, newCourse);
        double coefficient = newCourse.getGrade().getCoefficient();
        int credit = newCourse.getCredit();
        int baseGpa;

        if (credit <= 2) {
            baseGpa = lowCourseGpa.getGpa();;
        } else if (credit == 3) {
            baseGpa = mediumCourseGpa.getGpa();;
        } else if (credit == 4) {
            baseGpa = highCourseGpa.getGpa();;
        } else {
            baseGpa = 0;
        }

        double totalGpa = coefficient * credit * baseGpa;
        return new CourseDTO(newCourse, totalGpa);
    }

    @DeleteMapping("/{id}")
    public Course deleteCourse(@PathVariable("id") int id) {
        return courses.remove(id);
    }

}
