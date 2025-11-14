package com.infy.project.Dto;

public class CourseDTO {
	
	private String id;   // course code
    private String name;
    
    
    
    
	public CourseDTO(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
    
    
    
}
