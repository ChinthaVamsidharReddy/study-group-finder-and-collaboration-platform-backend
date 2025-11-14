package com.infy.project.Interface;


import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.project.model.Register;

public interface Logininterface extends JpaRepository<Register, Long>{
	
	Register findByemail(String username);
	
}
