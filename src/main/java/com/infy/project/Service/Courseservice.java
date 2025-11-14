package com.infy.project.Service;

import com.infy.project.model.Course;
import com.infy.project.Interface.CourseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Courseservice {
	
	@Autowired
	private CourseRepository courseRepository;
	
	public Courseservice(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<com.infy.project.model.Course> getAllCourses() {
        return courseRepository.findAll();
    }
}
