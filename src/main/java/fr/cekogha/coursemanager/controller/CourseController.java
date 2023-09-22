package fr.cekogha.coursemanager.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.cekogha.coursemanager.entity.Course;
import fr.cekogha.coursemanager.service.CourseService;
import fr.cekogha.coursemanager.service.ProducerService;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
@RequestMapping(value="/api/courses", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class CourseController {
	
	private final String RETURN_MSG = "Request succeed";

	private final CourseService courseService;
	private final ProducerService producerService;
	
	private final ObjectMapper mapper;
	
	public CourseController(final CourseService courseService, final ProducerService producerService, final ObjectMapper mapper) {
		this.courseService = courseService;
		this.producerService = producerService;
		this.mapper = mapper;
	}
	
	@GetMapping
	public ResponseEntity<String> getAllCourses() throws JsonProcessingException {
		List<Course> courses = courseService.findAll();
		log.info("Course list  : {}", courses);
		producerService.sendMessage(String.valueOf(mapper.writeValueAsString(courses)));
		return ResponseEntity.ok(RETURN_MSG);
	}
    
    @PostMapping
    public ResponseEntity<?> saveCustomer(@RequestBody Course course) throws JsonProcessingException {
		Course newCourse = courseService.saveCourse(course);
		log.info("Course has been created  : {}", newCourse);
		producerService.sendMessage(String.valueOf(mapper.writeValueAsString(newCourse)));
        return ResponseEntity.ok(RETURN_MSG);
    }
}
