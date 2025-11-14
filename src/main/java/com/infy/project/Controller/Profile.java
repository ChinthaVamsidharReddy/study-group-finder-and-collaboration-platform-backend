package com.infy.project.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.infy.project.Service.ProfileService;
import com.infy.project.model.Register;

@RestController
@RequestMapping("/profile")
public class Profile {
	
	@GetMapping("/profile")
	public String Data() {
		return "inside the profile";
	}

    @Autowired
    private ProfileService profileService;

    // Get user details by userId
    @GetMapping("/{userId}")
    public ResponseEntity<Register> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(profileService.getUserById(userId));
    }

    // Update user details (name, email, school, etc.)
    @PutMapping("/update/{userId}")
    public ResponseEntity<Register> updateUser(@PathVariable Long userId, @RequestBody Register updatedUser) {
        return ResponseEntity.ok(profileService.updateUser(userId, updatedUser));
    }

    // Update password
    @PutMapping("/update-password/{userId}")
    public ResponseEntity<String> updatePassword(@PathVariable Long userId, @RequestParam String newPassword) {
        return ResponseEntity.ok(profileService.updatePassword(userId, newPassword));
    }

    // Delete user profile
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        return ResponseEntity.ok(profileService.deleteUser(userId));
    }
}
