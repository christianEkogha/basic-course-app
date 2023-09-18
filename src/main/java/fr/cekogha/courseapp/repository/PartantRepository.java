package fr.cekogha.courseapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.cekogha.courseapp.entity.Partant;

public interface PartantRepository extends JpaRepository<Partant, Long> {
    boolean existsByNomIgnoreCase(String nom);
}
