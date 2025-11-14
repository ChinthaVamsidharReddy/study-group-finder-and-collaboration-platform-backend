package com.infy.project.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.project.Dto.PeerDTO;
import com.infy.project.Service.Courseservice;
import com.infy.project.Service.EnrollmentService;
import com.infy.project.Service.PeerSuggestionService;
import com.infy.project.model.Course;

@RestController
@RequestMapping("/courses")
public class CourseController {
	
	@GetMapping("/courses")
	public String check() {
		return "ok inside the courses";
	}
	
	@Autowired
	private Courseservice courseservice;
	@GetMapping("/detailes")
    public List<Course> getAllCourses() {
        return courseservice.getAllCourses();
    }

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/enroll/{userId}/{courseCode}")
    public ResponseEntity<String> enrollCourse(@PathVariable Long userId, @PathVariable String courseCode) {
        return ResponseEntity.ok(enrollmentService.enrollCourse(userId, courseCode));
    }

    @DeleteMapping("/remove/{userId}/{courseCode}")
    public ResponseEntity<String> removeCourse(@PathVariable Long userId, @PathVariable String courseCode) {
        return ResponseEntity.ok(enrollmentService.removeCourse(userId, courseCode));
    }

    @GetMapping("/enrolled/{userId}")
    public ResponseEntity<List<Course>> getEnrolledCourses(@PathVariable Long userId) {
        return ResponseEntity.ok(enrollmentService.getEnrolledCourses(userId));
    }
    
    @Autowired
    private PeerSuggestionService peerSuggestionService;
    
    
    @GetMapping("/{userId}/peers")
    public ResponseEntity<List<PeerDTO>> suggestPeers(@PathVariable Long userId) {
        List<PeerDTO> peers = peerSuggestionService.suggestPeers(userId);
        return ResponseEntity.ok(peers);
    }

}
