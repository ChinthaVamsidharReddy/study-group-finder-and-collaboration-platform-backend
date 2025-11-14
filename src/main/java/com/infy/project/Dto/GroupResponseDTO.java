package com.infy.project.Dto; // use your actual package

import java.time.LocalDateTime;

public class GroupResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String courseId;
    private String coursename;
    private String privacy;
    private Long createdBy;
    private LocalDateTime createdAt;
    private int memberCount;

    // default ctor
    public GroupResponseDTO() {}

    // <-- important: constructor accepting Group
    public GroupResponseDTO(com.infy.project.model.Group group, int memberCount) {
        if (group == null) return;
        this.id = group.getId();
        this.name = group.getName();
        this.description = group.getDescription();
        this.courseId = group.getCourseId();
        this.coursename=group.getCoursename();
        this.privacy = group.getPrivacy();
        this.createdBy = group.getCreatedBy();
        this.createdAt = group.getCreatedAt();
        this.memberCount=memberCount;
    }

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

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public int getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}

    // getters & setters
	
	
	
	
    
    
    
    
}
