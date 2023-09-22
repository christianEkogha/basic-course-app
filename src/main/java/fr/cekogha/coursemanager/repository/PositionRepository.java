package fr.cekogha.coursemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.cekogha.coursemanager.entity.Position;
import fr.cekogha.coursemanager.entity.PositionKey;

public interface PositionRepository extends JpaRepository<Position, PositionKey> {
}
