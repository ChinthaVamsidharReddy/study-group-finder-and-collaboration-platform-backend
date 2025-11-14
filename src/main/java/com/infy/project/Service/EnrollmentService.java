package com.infy.project.Service;

import java.rmi.registry.Registry;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.project.Interface.CourseRepository;
import com.infy.project.Interface.EnrollmentRepository;
import com.infy.project.Interface.Logininterface;
import com.infy.project.model.Course;
import com.infy.project.model.Enrollment;
import com.infy.project.model.Register;

@Service
public class EnrollmentService {

    @Autowired
    private Logininterface logininterface;

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private EnrollmentRepository enrollmentRepo;

    // Enroll in a course
    public String enrollCourse(Long userId, String courseCode) {
        Register register = logininterface.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Course course = courseRepo.findByCourseCode(courseCode)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        boolean alreadyEnrolled = enrollmentRepo.findById(userId)
                .stream().anyMatch(e -> e.getCourse().equals(course));

        if (alreadyEnrolled) {
            return "Already enrolled in this course.";
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setUser(register);
        enrollment.setCourse(course);
        enrollmentRepo.save(enrollment);

        return "Enrolled successfully in course: " + course.getCourseName();
    }

    // Remove enrollment
    public String removeCourse(Long userId, String courseCode) {
        Register register = logininterface.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Course course = courseRepo.findByCourseCode(courseCode)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Enrollment enrollment = enrollmentRepo.findByRegister(register)
                .stream()
                .filter(e -> e.getCourse().equals(course))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not enrolled in this course"));

        enrollmentRepo.delete(enrollment);
        return "Course removed successfully!";
    }

    // Get user enrolled courses
    public List<Course> getEnrolledCourses(Long userId) {
        Register register = logininterface.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return enrollmentRepo.findByRegister(register)
                .stream().map(Enrollment::getCourse)
                .toList();
    }
}
