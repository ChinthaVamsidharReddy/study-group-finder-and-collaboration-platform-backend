// package com.infy.project.Controller;

// import java.util.Map;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.infy.project.Service.Loginservice;
// import com.infy.project.Service.MailService;
// import com.infy.project.Service.OtpService;
// import com.infy.project.model.Register;

// @RestController
// @RequestMapping("/auth")
// public class ForgotPasswordContoller {
	
// 	@Autowired
// 	private Loginservice loginservice;
	
// 	@Autowired
// 	private OtpService otpService;
	
// 	@Autowired
// 	private MailService mailService;
	

// 	@PostMapping("/forgot-password")
// 	public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
// 	    String email = request.get("email");
// 	    Register register = loginservice.findByEmail(email);
// 	    if (register == null) return ResponseEntity.badRequest().body("User not found");

// 	    String otp = otpService.generateOtp(email); // store OTP in-memory or DB with expiry
// 	    mailService.sendOtpEmail(email, otp);

// 	    return ResponseEntity.ok("OTP sent");
// 	}
	
// 	@PostMapping("/verify-otp")
// 	public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> request) {
// 	    String email = request.get("email");
// 	    String otp = request.get("otp");

// 	    if (!otpService.verifyOtp(email, otp)) {
// 	        return ResponseEntity.badRequest().body("OTP invalid or expired");
// 	    }
// 	    return ResponseEntity.ok("OTP verified");
// 	}
	
// 	@PostMapping("/reset-password")
// 	public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
// 	    String email = request.get("email");
// 	    String newPassword = request.get("newPassword");

// 	    Register register = loginservice.findByEmail(email);
// 	    if (register == null) return ResponseEntity.badRequest().body("User not found");

// 	    // Encode password with bcrypt
// 	    BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);
// 	    String encodedPassword = encoder.encode(newPassword);
// 	    register.setPassword(encodedPassword);
// 	    loginservice.save(register);

// 	    return ResponseEntity.ok("Password updated successfully");
// 	}


// }
