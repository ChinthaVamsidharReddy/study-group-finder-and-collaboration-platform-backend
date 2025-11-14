package com.infy.project.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.infy.project.Interface.Logininterface;
import com.infy.project.model.Register;

@Service
public class ProfileService {

    @Autowired
    private Logininterface loginInterface;
    
	BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    public Register getUserById(Long userId) {
        return loginInterface.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Register updateUser(Long userId, Register updatedUser) {
        Register existingUser = loginInterface.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update fields (avoid changing ID)
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setSecondarySchool(updatedUser.getSecondarySchool());
        existingUser.setSecondarySchoolPassingYear(updatedUser.getSecondarySchoolPassingYear());
        existingUser.setSecondarySchoolPercentage(updatedUser.getSecondarySchoolPercentage());
        existingUser.setHigherSecondarySchool(updatedUser.getHigherSecondarySchool());
        existingUser.setHigherSecondaryPassingYear(updatedUser.getHigherSecondaryPassingYear());
        existingUser.setHigherSecondaryPercentage(updatedUser.getHigherSecondaryPercentage());
        existingUser.setUniversityName(updatedUser.getUniversityName());
        existingUser.setUniversityPassingYear(updatedUser.getUniversityPassingYear());
        existingUser.setUniversityPassingGPA(updatedUser.getUniversityPassingGPA());
        System.out.println("\n\n\n\n\n\n\n\n\n"+updatedUser.getMajor()+"\n\n\n\n\n\n\n\n");
        existingUser.setMajor(updatedUser.getMajor());

        if (updatedUser.getAvatar() != null && !updatedUser.getAvatar().isEmpty()) {
            existingUser.setAvatar(updatedUser.getAvatar());
        }
        
        return loginInterface.save(existingUser);
    }

    public String updatePassword(Long userId, String newPassword) {
        Register existingUser = loginInterface.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        
        existingUser.setPassword(encoder.encode(newPassword)); 
        loginInterface.save(existingUser);

        return "Password updated successfully!";
    }

    public String deleteUser(Long userId) {
        Register existingUser = loginInterface.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        loginInterface.delete(existingUser);

        return "User deleted successfully!";
    }
}
