package com.infy.project.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email") 
})
public class Register {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "LONGTEXT")
    private String avatar;

    private String secondarySchool;
    private int secondarySchoolPassingYear;
    private double secondarySchoolPercentage;

    private String higherSecondarySchool;
    private int higherSecondaryPassingYear;
    private double higherSecondaryPercentage;

    private String universityName;
    private int universityPassingYear;
    private double universityPassingGPA;
    private String major;

    // ✅ Many-to-Many relationship with Course
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_courses",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;

    // Getters and Setters
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//
//    public String getName() { return name; }
//    public void setName(String name) { this.name = name; }
//
//    public String getEmail() { return email; }
//    public void setEmail(String email) { this.email = email; }
//
//    public String getPassword() { return password; }
//    public void setPassword(String password) { this.password = password; }
//
//    public String getAvatar() { return avatar; }
//    public void setAvatar(String avatar) { this.avatar = avatar; }
//
//    public String getSecondarySchool() { return secondarySchool; }
//    public void setSecondarySchool(String secondarySchool) { this.secondarySchool = secondarySchool; }
//
//    public int getSecondarySchoolPassingYear() { return secondarySchoolPassingYear; }
//    public void setSecondarySchoolPassingYear(int secondarySchoolPassingYear) { this.secondarySchoolPassingYear = secondarySchoolPassingYear; }
//
//    public double getSecondarySchoolPercentage() { return secondarySchoolPercentage; }
//    public void setSecondarySchoolPercentage(double secondarySchoolPercentage) { this.secondarySchoolPercentage = secondarySchoolPercentage; }
//
//    public String getHigherSecondarySchool() { return higherSecondarySchool; }
//    public void setHigherSecondarySchool(String higherSecondarySchool) { this.higherSecondarySchool = higherSecondarySchool; }
//
//    public int getHigherSecondaryPassingYear() { return higherSecondaryPassingYear; }
//    public void setHigherSecondaryPassingYear(int higherSecondaryPassingYear) { this.higherSecondaryPassingYear = higherSecondaryPassingYear; }
//
//    public double getHigherSecondaryPercentage() { return higherSecondaryPercentage; }
//    public void setHigherSecondaryPercentage(double higherSecondaryPercentage) { this.higherSecondaryPercentage = higherSecondaryPercentage; }
//
//    public String getUniversityName() { return universityName; }
//    public void setUniversityName(String universityName) { this.universityName = universityName; }
//
//    public int getUniversityPassingYear() { return universityPassingYear; }
//    public void setUniversityPassingYear(int universityPassingYear) { this.universityPassingYear = universityPassingYear; }
//
//    public double getUniversityPassingGPA() { return universityPassingGPA; }
//    public void setUniversityPassingGPA(double universityPassingGPA) { this.universityPassingGPA = universityPassingGPA; }
//
//    public String getMajor() { return major; }
//    public void setMajor(String major) { this.major = major; }
    
    

//    public List<Course> getCourses() { return courses; }
//	public void setCourses(List<Course> courses) { this.courses = courses; }
    
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getSecondarySchool() {
		return secondarySchool;
	}
	public void setSecondarySchool(String secondarySchool) {
		this.secondarySchool = secondarySchool;
	}
	public int getSecondarySchoolPassingYear() {
		return secondarySchoolPassingYear;
	}
	public void setSecondarySchoolPassingYear(int secondarySchoolPassingYear) {
		this.secondarySchoolPassingYear = secondarySchoolPassingYear;
	}
	public double getSecondarySchoolPercentage() {
		return secondarySchoolPercentage;
	}
	public void setSecondarySchoolPercentage(double secondarySchoolPercentage) {
		this.secondarySchoolPercentage = secondarySchoolPercentage;
	}
	public String getHigherSecondarySchool() {
		return higherSecondarySchool;
	}
	public void setHigherSecondarySchool(String higherSecondarySchool) {
		this.higherSecondarySchool = higherSecondarySchool;
	}
	public int getHigherSecondaryPassingYear() {
		return higherSecondaryPassingYear;
	}
	public void setHigherSecondaryPassingYear(int higherSecondaryPassingYear) {
		this.higherSecondaryPassingYear = higherSecondaryPassingYear;
	}
	public double getHigherSecondaryPercentage() {
		return higherSecondaryPercentage;
	}
	public void setHigherSecondaryPercentage(double higherSecondaryPercentage) {
		this.higherSecondaryPercentage = higherSecondaryPercentage;
	}
	public String getUniversityName() {
		return universityName;
	}
	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}
	public int getUniversityPassingYear() {
		return universityPassingYear;
	}
	public void setUniversityPassingYear(int universityPassingYear) {
		this.universityPassingYear = universityPassingYear;
	}
	public double getUniversityPassingGPA() {
		return universityPassingGPA;
	}
	public void setUniversityPassingGPA(double universityPassingGPA) {
		this.universityPassingGPA = universityPassingGPA;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public List<Course> getCourses() {
		return courses;
	}
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	
}
