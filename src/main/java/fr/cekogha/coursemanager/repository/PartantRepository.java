package fr.cekogha.coursemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.cekogha.coursemanager.entity.Partant;

public interface PartantRepository extends JpaRepository<Partant, Long> {
    Partant findByIdPartant(Long partantId);
}
