package fr.cekogha.coursemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.cekogha.coursemanager.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
	Course findByIdCourse(long idCourse);
}
