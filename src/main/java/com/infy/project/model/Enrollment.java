package com.infy.project.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Register register;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private LocalDateTime enrolledAt = LocalDateTime.now();

	public Enrollment() {
		super();
	}

	public Enrollment(Long id, Register register, Course course, LocalDateTime enrolledAt) {
		super();
		this.id = id;
		this.register = register;
		this.course = course;
		this.enrolledAt = enrolledAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Register getUser() {
		return register;
	}

	public void setUser(Register register) {
		this.register = register;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public LocalDateTime getEnrolledAt() {
		return enrolledAt;
	}

	public void setEnrolledAt(LocalDateTime enrolledAt) {
		this.enrolledAt = enrolledAt;
	}
    
    
    
}
