package fr.cekogha.coursemanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.cekogha.coursemanager.dto.CourseDTO;
import fr.cekogha.coursemanager.entity.Course;
import fr.cekogha.coursemanager.service.CourseService;
import fr.cekogha.coursemanager.service.ProducerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/courses")
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


	@Operation(summary = "Get all courses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course list has been retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<String> getAllCourses() throws JsonProcessingException {
        List<Course> courses = courseService.findAll();
        log.info("Course list  : {}", courses);
        producerService.sendMessage(String.valueOf(mapper.writeValueAsString(courses)));
        return ResponseEntity.ok(RETURN_MSG);
    }

	@Operation(summary = "Create a course")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Course has been created successfully")
	})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveCustomer(@RequestBody @Valid CourseDTO courseDto) throws JsonProcessingException {
        Course newCourse = courseService.saveCourse(courseDto);
        log.info("Course has been created  : {}", newCourse);
        producerService.sendMessage(String.valueOf(mapper.writeValueAsString(newCourse)));
        return ResponseEntity.ok(RETURN_MSG);
    }
}
