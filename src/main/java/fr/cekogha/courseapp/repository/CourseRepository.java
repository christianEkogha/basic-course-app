package fr.cekogha.courseapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import fr.cekogha.courseapp.entity.Course;
import fr.cekogha.courseapp.entity.Partant;

public interface CourseRepository extends JpaRepository<Course, Long>, QueryByExampleExecutor<Course> {
    List<Course> findAllByPartants(Partant partant);
}
