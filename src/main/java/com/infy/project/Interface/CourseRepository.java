package com.infy.project.Interface;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.project.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCourseCode(String courseCode);
}

