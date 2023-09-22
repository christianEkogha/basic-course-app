package fr.cekogha.coursemanager.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import fr.cekogha.coursemanager.dto.CourseDTO;
import fr.cekogha.coursemanager.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.cekogha.coursemanager.entity.Course;
import fr.cekogha.coursemanager.entity.Partant;
import fr.cekogha.coursemanager.entity.Position;
import fr.cekogha.coursemanager.repository.CourseRepository;

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private PartantService partantService;

	public List<Course> findAll(){
		return courseRepository.findAll();
	}
	
	public Course saveCourse(CourseDTO courseDTO) {
		Course newCourse = new Course();
		newCourse.setNomCourse(courseDTO.getNomCourse());
		newCourse.setJourCourse(courseDTO.getJourCourse());
		AtomicInteger numero = new AtomicInteger(1);
		newCourse.getPositions().addAll(courseDTO.getPartantIds().stream().distinct().map(partantId -> {

			Partant partant = partantService.findPartantById(partantId);
			if(partant == null)
				throw new NotFoundException("No Partants found for id : " + partantId);

			Position newPosition = new Position();
			newPosition.setCourse(newCourse);
			newPosition.setPartant(partant);
			newPosition.setNumero(numero.getAndIncrement());
			return newPosition;
		}).collect(Collectors.toList()));
		return courseRepository.save(newCourse);
	}
}
