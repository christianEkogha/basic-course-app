package fr.cekogha.courseapp.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.cekogha.courseapp.dto.CourseDTO;
import fr.cekogha.courseapp.service.CourseService;
import fr.cekogha.courseapp.service.ProducerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/** The type Course controller. */
@RestController
@RequestMapping(value = "/api/courses", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class CourseController {

  private final String ACCEPTED_MSG = "Request Accepted";

  private final CourseService courseService;
  private final ProducerService producerService;

  private final ObjectMapper mapper;

  /**
   * Instantiates a new Course controller.
   *
   * @param courseService the course service
   * @param producerService the producer service
   * @param mapper the mapper
   */
  public CourseController(
      final CourseService courseService,
      final ProducerService producerService,
      final ObjectMapper mapper) {
    this.courseService = courseService;
    this.producerService = producerService;
    this.mapper = mapper;
  }

  /**
   * Gets all courses.
   *
   * @return the all courses
   * @throws JsonProcessingException the json processing exception
   */
  @GetMapping
  public ResponseEntity<String> getAllCourses() throws JsonProcessingException {
    List<CourseDTO> coursesDTO = courseService.findAll();
    log.info("Course list  : {}", coursesDTO);
    producerService.sendMessage(String.valueOf(mapper.writeValueAsString(coursesDTO)));
    return ResponseEntity.accepted().body(ACCEPTED_MSG);
  }

  /**
   * Create course response entity.
   *
   * @param courseDTO the course dto
   * @return the response entity
   */
  @PostMapping
  public ResponseEntity<String> createCourse(@RequestBody @Valid CourseDTO courseDTO) {
    Long courseId = courseService.createCourse(courseDTO);
    log.info("Course a été créée : id = {}", courseId);
    producerService.sendMessage(String.valueOf(courseId));
    return ResponseEntity.accepted().body(ACCEPTED_MSG);
  }
}
