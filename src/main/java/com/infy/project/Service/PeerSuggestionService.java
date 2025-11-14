package com.infy.project.Service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.project.Dto.CourseDTO;
import com.infy.project.Dto.PeerDTO;
import com.infy.project.Interface.EnrollmentRepository;
import com.infy.project.Interface.RegisterRepository;
import com.infy.project.model.Course;
import com.infy.project.model.Enrollment;
import com.infy.project.model.Register;

@Service
public class PeerSuggestionService {

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public List<PeerDTO> suggestPeers(Long userId) {

        // Fetch current user
        Register currentUser = registerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch all courses of current user
        List<Enrollment> userEnrollments = enrollmentRepository.findByRegisterId(userId);
        Set<Course> userCourses = userEnrollments.stream()
                .map(Enrollment::getCourse)
                .collect(Collectors.toSet());

        if (userCourses.isEmpty()) return Collections.emptyList();

        // Fetch all other users enrolled in any of these courses
        Set<Register> suggestedPeers = new HashSet<>();

        for (Course course : userCourses) {
            List<Enrollment> peersInCourse = enrollmentRepository.findByCourse(course);
            for (Enrollment e : peersInCourse) {
                Register peer = e.getUser();
                if (!peer.getId().equals(userId)) {
                    suggestedPeers.add(peer);
                }
            }
        }

        List<PeerDTO> result = suggestedPeers.stream().map(peer -> {
            List<CourseDTO> peerCourses = enrollmentRepository.findByRegisterId(peer.getId())
                .stream()
                .map(e -> new CourseDTO(e.getCourse().getCourseCode(), e.getCourse().getCourseName()))
                .collect(Collectors.toList());

            return new PeerDTO(peer.getId(), peer.getName(), peer.getEmail(), peer.getMajor(), peer.getUniversityName(), peerCourses);
        }).collect(Collectors.toList());

        

        return result;
    }
}
