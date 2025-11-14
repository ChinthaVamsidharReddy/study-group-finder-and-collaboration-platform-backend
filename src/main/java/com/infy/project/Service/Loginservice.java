package com.infy.project.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.infy.project.Dto.LoginDto;
import com.infy.project.Dto.RegisterDto;
import com.infy.project.Interface.Logininterface;
import com.infy.project.model.Register;

@Service
public class Loginservice {
	
	public Long id=null;
	public String email =null;
	public String name=null;
	@Autowired
	private Logininterface logininterface;
	BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);
	
	public Long getId() {
		return id;
	}
	public boolean valid(LoginDto logindto) {
	    if (logindto.getEmail() == null || logindto.getEmail().trim().isEmpty() ||
	        logindto.getPassword() == null || logindto.getPassword().trim().isEmpty()) {
	        return false;
	    }

	    Register data = logininterface.findByemail(logindto.getEmail());
	    if (data != null) {
	    	id = data.getId();
	    	name=data.getName();// Save user id for JWT
	    	System.out.println("\n\n\n\n\n\n\n"+id+"\n\n\n\n\n"+name+"\n\n\n\n"+data.getEmail()+"\n\n\n\n\n\n"+data.getPassword()+"\n\n\n\n\n\n\n\n");
	        return encoder.matches(logindto.getPassword(), data.getPassword());
	    }
	    return false;
	}
	
	public Register findByEmail(String email) {
	    return logininterface.findByemail(email);
	}
	
	public void save(Register user) {
	    logininterface.save(user);
	}

		

	public String register(RegisterDto registerdto) {
	    if (registerdto.getEmail() == null || registerdto.getEmail().trim().isEmpty() ||
	        registerdto.getName() == null || registerdto.getName().trim().isEmpty() ||
	        registerdto.getPassword() == null || registerdto.getPassword().trim().isEmpty()) {
	        return "Some fields are empty!";
	    }

	    if (logininterface.findByemail(registerdto.getEmail()) != null) {
	        return "Email is already taken!";
	    }

	    Register model = new Register();
	    model.setName(registerdto.getName());
	    model.setEmail(registerdto.getEmail());
	    model.setPassword(registerdto.getPassword());
	    
	    // school
	    model.setSecondarySchool(registerdto.getSecondarySchool());
	    model.setSecondarySchoolPassingYear(registerdto.getSecondarySchoolPassingYear());
	    model.setSecondarySchoolPercentage(registerdto.getSecondarySchoolPercentage());
	    
	    //inter
	    model.setHigherSecondarySchool(registerdto.getHigherSecondarySchool());
	    model.setHigherSecondaryPassingYear(registerdto.getHigherSecondaryPassingYear());
	    model.setHigherSecondaryPercentage(registerdto.getHigherSecondaryPercentage());
	    
	    //University
	    model.setUniversityName(registerdto.getUniversityName());
	    model.setUniversityPassingGPA(registerdto.getUniversityPassingGPA());
	    model.setUniversityPassingYear(registerdto.getUniversityPassingYear());
	    model.setMajor(registerdto.getMajor());
	    model.setPassword(encoder.encode(registerdto.getPassword()));
	    logininterface.save(model);
	    
	    id=model.getId();
	    email=model.getEmail();
	    name=model.getName();
	    
	    System.out.println("\n\n\n\n\n\n\n\n\n\n\n"
	    		+registerdto.getSecondarySchool()+registerdto.getSecondarySchoolPassingYear()+registerdto.getSecondarySchoolPercentage()+registerdto.getMajor()+"\n\n\n\n\n\n\n\n");
	    
	    

	    return "Inserted the data in database";
	}
}

