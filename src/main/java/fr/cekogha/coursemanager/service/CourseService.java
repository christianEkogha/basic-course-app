package fr.cekogha.coursemanager.service;

import java.util.List;
import java.util.stream.Collectors;

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
	
	public Course saveCourse(Course course) {
		Course newCourse = new Course();
		newCourse.setNomCourse(course.getNomCourse());
		newCourse.setJourCourse(course.getJourCourse());
		newCourse.getPositions().addAll(course.getPositions().stream().map(position -> {
			Position newPosition = new Position();
			newPosition.setCourse(newCourse);
			Partant partant = partantService.findPartantById(position.getPartant().getIdPartant());
			newPosition.setPartant(partant);
			newPosition.setNumero(position.getNumero());
			return newPosition;
		}).collect(Collectors.toList()));
		return courseRepository.save(newCourse);
	}
}
