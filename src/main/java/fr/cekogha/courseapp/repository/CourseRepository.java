package fr.cekogha.courseapp.repository;

import fr.cekogha.courseapp.entity.Course;
import fr.cekogha.courseapp.entity.Partant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * The interface Course repository.
 */
public interface CourseRepository extends JpaRepository<Course, Long>, QueryByExampleExecutor<Course> {
    /**
     * Find all by partants list.
     *
     * @param partant the partant
     * @return the list
     */
List<Course> findAllByPartants(Partant partant);
}
