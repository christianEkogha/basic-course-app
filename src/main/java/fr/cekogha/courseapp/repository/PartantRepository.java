package fr.cekogha.courseapp.repository;

import fr.cekogha.courseapp.entity.Partant;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Partant repository.
 */
public interface PartantRepository extends JpaRepository<Partant, Long> {
    /**
     * Exists by nom ignore case boolean.
     *
     * @param nom the nom
     * @return the boolean
     */
boolean existsByNomIgnoreCase(String nom);
}
