package com.infy.project.Dto;

public class LoginDto {
	
	private String email;
	private String password;
	private int id;
	
	
	
	
	public LoginDto() {
		super();
	}
	
	
	
	
	public LoginDto(String username, String password, int id) {
		super();
		this.email = username;
		this.password = password;
		this.id=id;
		
	}

	


	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
	}




	public int getId() {
		return id;
	}




	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
}
