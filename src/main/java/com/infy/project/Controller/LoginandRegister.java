package com.infy.project.Controller;




import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.project.Dto.LoginDto;
import com.infy.project.Dto.RegisterDto;
import com.infy.project.Interface.Logininterface;
import com.infy.project.Service.Loginservice;
import com.infy.project.Service.MailService;
import com.infy.project.Service.OtpService;
import com.infy.project.model.Register;
import com.infy.project.security.JWTUtility;


@RestController
@RequestMapping("/auth")
public class LoginandRegister {
	
	@GetMapping("/hello")
	public static String meth() {
		return "hello controler";
	}
	
	@Autowired
	private JWTUtility jwtUtility;
	
	@Autowired
	private Loginservice loginservice;
	
	@Autowired
	private Logininterface logininterface;
	
	

	@PostMapping("/login")
	public ResponseEntity<?> logincheck(@RequestBody LoginDto logindto) {
	    if (loginservice.valid(logindto)) {
	        String id = String.valueOf(loginservice.getId());
	        String token = jwtUtility.generateToken(logindto.getEmail(), id);
	        String name = loginservice.name;
	        // return token + optional user info
	        Map<String, Object> response = new HashMap<>();
	        response.put("token", token);
	        response.put("user", Map.of(
	            "id", id,
	            "email", logindto.getEmail(),
	            "name",name
	        ));
	        // System.out.println("\n\n\n\n\n "+name+" \n\n\n\n login completed \n\n\n\n\n\n\n");
	        return ResponseEntity.ok(response);
	    } else {
	        return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
	    }
	}


	
	
	@PostMapping("/register")
	public ResponseEntity<?>  registercheck(@RequestBody RegisterDto registerdto) {
	    String checkdata = loginservice.register(registerdto);

	    if (checkdata.equals("Inserted the data in database")) {
	    	String token = jwtUtility.generateToken(registerdto.getEmail(), "NEW_USER");
	    	Map<String, Object> response = new HashMap<>();
	        response.put("token", token);
	        response.put("user", Map.of(
	            "id", loginservice.id,
	            "email", loginservice.email,
	            "name",loginservice.name
	        ));
	        
	        return ResponseEntity.ok(response);
	    } else {
	        return ResponseEntity.badRequest().body(checkdata);
	    }
	}

	
	
	
	
	
	

	
	@Autowired
	private OtpService otpService;
	
	@Autowired
	private MailService mailService;
	

	@PostMapping("/forgot-password")
	public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
	    String email = request.get("email");
	    Register register = loginservice.findByEmail(email);
	    if (register == null) return ResponseEntity.badRequest().body("User not found");

	    String otp = otpService.generateOtp(email); // store OTP in-memory or DB with expiry
	    mailService.sendOtpEmail(email, otp);

	    return ResponseEntity.ok("OTP sent");
	}
	
	@PostMapping("/verify-otp")
	public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> request) {
	    String email = request.get("email");
	    String otp = request.get("otp");

	    if (!otpService.verifyOtp(email, otp)) {
	        return ResponseEntity.badRequest().body("OTP invalid or expired");
	    }
	    return ResponseEntity.ok("OTP verified");
	}
	
	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
	    String email = request.get("email");
	    String newPassword = request.get("newPassword");

	    Register register = loginservice.findByEmail(email);
	    if (register == null) return ResponseEntity.badRequest().body("User not found");

	    // Encode password with bcrypt
	    BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);
	    String encodedPassword = encoder.encode(newPassword);
	    register.setPassword(encodedPassword);
	    loginservice.save(register);

	    return ResponseEntity.ok("Password updated successfully");
	}

	
	

}
