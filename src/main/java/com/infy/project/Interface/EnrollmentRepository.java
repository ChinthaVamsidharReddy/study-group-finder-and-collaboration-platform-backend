package com.infy.project.Interface;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.project.model.Course;
import com.infy.project.model.Enrollment;
import com.infy.project.model.Register;
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByRegister(Register register);
    void deleteByRegister(Register register);
    // Fetch all courses of a user
    List<Enrollment> findByRegisterId(Long userId);

    // Fetch all users enrolled in a course
    List<Enrollment> findByCourse(Course course);
}