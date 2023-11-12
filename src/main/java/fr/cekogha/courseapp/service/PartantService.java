package fr.cekogha.courseapp.service;

import fr.cekogha.courseapp.dto.PartantDTO;
import fr.cekogha.courseapp.entity.Course;
import fr.cekogha.courseapp.entity.Partant;
import fr.cekogha.courseapp.repository.PartantRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * The type Partant service.
 */
@Service
@Transactional
public class PartantService {

	private final PartantRepository partantRepository;

	/**
     * Instantiates a new Partant service.
     *
     * @param partantRepository the partant repository
     */
public PartantService(final PartantRepository partantRepository) {
		this.partantRepository = partantRepository;
	}

	/**
     * Find all list.
     *
     * @return the list
     */
public List<PartantDTO> findAll() {
		final List<Partant> partants = partantRepository.findAll(Sort.by("id"));
		return partants.stream().map(this::partantToDTO).toList();
	}

	/**
     * Create partant long.
     *
     * @param partantDTO the partant dto
     * @return the long
     */
public Long createPartant(final PartantDTO partantDTO) {
		Partant partant = new Partant();
		partant.setId(partantDTO.getId());
		partant.setNom(partantDTO.getNom());
		Partant partantSaved = partantRepository.save(partant);
		return Optional.of(partantSaved.getId()).orElseThrow(RuntimeException::new);
	}

	/**
     * Partant to dto partant dto.
     *
     * @param partant the partant
     * @return the partant dto
     */
public PartantDTO partantToDTO(Partant partant) {
		PartantDTO partantDTO = new PartantDTO();
		partantDTO.setNom(partant.getNom());
		partantDTO.setId(partant.getId());
		Set<Long> courseIds = partant.getCourses().stream().map(Course::getId).collect(Collectors.toSet());
		partantDTO.setCourseIds(courseIds);
		return partantDTO;
	}
}
