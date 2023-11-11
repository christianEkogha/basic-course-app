package fr.cekogha.courseapp.service;

import fr.cekogha.courseapp.dto.CourseDTO;
import fr.cekogha.courseapp.entity.Course;
import fr.cekogha.courseapp.entity.Partant;
import fr.cekogha.courseapp.exception.NotFoundException;
import fr.cekogha.courseapp.repository.CourseRepository;
import fr.cekogha.courseapp.repository.PartantRepository;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * The type Course service.
 */
@Service
@Transactional
@Slf4j
public class CourseService {

	private final CourseRepository courseRepository;
	private final PartantRepository partantRepository;

	/**
     * Instantiates a new Course service.
     *
     * @param repository the repository
     * @param partantRepository the partant repository
     */
public CourseService(final CourseRepository repository, final PartantRepository partantRepository) {
		this.courseRepository = repository;
		this.partantRepository = partantRepository;
	}

	/**
     * Find all list.
     *
     * @return the list
     */
public List<CourseDTO> findAll() {
		final List<Course> courses = courseRepository.findAll(Sort.by("id"));
		return courses.stream().map(this::courseToDTO).toList();
	}

	/**
     * Create course long.
     *
     * @param courseDTO the course dto
     * @return the long
     */
public Long createCourse(final CourseDTO courseDTO) {
		Course courseSaved = courseRepository.save(dtoToCourse(courseDTO));
		return Optional.of(courseSaved.getId()).orElseThrow(RuntimeException::new);
	}

	/**
     * Course to dto course dto.
     *
     * @param course the course
     * @return the course dto
     */
public CourseDTO courseToDTO(Course course) {
		CourseDTO courseDTO = new CourseDTO();
		courseDTO.setNumero(course.getId());
		courseDTO.setNom(course.getNom());
		courseDTO.setJour(course.getJour());
		Set<Long> partantIds = course.getPartants().stream().map(Partant::getId).collect(Collectors.toSet());
		courseDTO.setPartantIds(partantIds);
		return courseDTO;
	}

	/**
     * Dto to course course.
     *
     * @param courseDTO the course dto
     * @return the course
     */
public Course dtoToCourse(CourseDTO courseDTO) {
		Course course = new Course();
		course.setId(courseDTO.getNumero());
		course.setJour(courseDTO.getJour());
		course.setNom(courseDTO.getNom());

			final List<Partant> partants = partantRepository.findAllById(
					courseDTO.getPartantIds() == null ? Collections.emptyList() : courseDTO.getPartantIds());

			if (partants == null || partants.isEmpty())
				throw new NotFoundException(
						"aucun partant trouvé à partir des id renseigné partantIds = " + courseDTO.getPartantIds());

			if (partants.size() < 3)
				throw new NotFoundException("moins de 3 partants minimum détecté");

			if (partants.size() != (courseDTO.getPartantIds() == null ? 0 : courseDTO.getPartantIds().size()))
				throw new NotFoundException("un des partants non trouvé");

			course.setPartants(partants.stream().collect(Collectors.toSet()));
		return course;
	}
}
