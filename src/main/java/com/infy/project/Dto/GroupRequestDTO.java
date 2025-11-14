package com.infy.project.Dto;


public class GroupRequestDTO {

    private String name;
    private String description;
    private String courseId;
    private String coursename;
    private String privacy;
    private String code;
    
    private Long userId;
    private String userName;
    private String userEmail;


    public GroupRequestDTO() {}

    

 public GroupRequestDTO(String name, String description, String courseId, String privacy, Long userId,
			String userName, String userEmail, String coursename) {
		super();
		this.name = name;
		this.description = description;
		this.courseId = courseId;
		this.privacy = privacy;
		this.userId = userId;
		this.coursename=coursename;
		this.userName = userName;
		this.userEmail = userEmail;
	}



// Getters and Setters
    
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	
	
	

	public String getCoursename() {
		return coursename;
	}



	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}



	public String getPrivacy() {
		return privacy;
	}

	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}



	public Long getUserId() {
		return userId;
	}



	public void setUserId(Long userId) {
		this.userId = userId;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getUserEmail() {
		return userEmail;
	}



	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}



	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}
	
	
	

    
    
    
    
}

