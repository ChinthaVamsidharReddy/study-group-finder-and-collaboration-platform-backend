package com.infy.project.Dto;

import java.util.List;
import java.util.List;

public class PeerDTO {
    private Long id;
    private String name;
    private String email;
    private String major;
    private String universityName;
    private List<CourseDTO> courses;

    // Constructor with parameters
    public PeerDTO(Long id, String name, String email, String major, String universityName, List<CourseDTO> courses) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.major = major;
        this.universityName = universityName;
        this.courses = courses;
    }

    // Default constructor (needed for JSON deserialization)
    public PeerDTO() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getUniversityName() {
		return universityName;
	}

	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}

	public List<CourseDTO> getCourses() {
		return courses;
	}

	public void setCourses(List<CourseDTO> courses) {
		this.courses = courses;
	}
    
    

}