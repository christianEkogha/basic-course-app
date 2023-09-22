package fr.cekogha.coursemanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.cekogha.coursemanager.entity.Partant;
import fr.cekogha.coursemanager.repository.PartantRepository;

@Service
public class PartantService {
    @Autowired
    private PartantRepository partantRepository;

    public Partant findPartantById(Long idPartant) {
        return partantRepository.findByIdPartant(idPartant);
    }

	public List<Partant> findAll() {
		List<Partant> partants = new ArrayList<>();
		partantRepository.findAll().forEach(partant -> partants.add(partant));
		return partants;
	}
	
	public Partant createPartant(final String nom) {
		Partant newPartant = new Partant();
		newPartant.setNomPartant(nom);
		return partantRepository.save(newPartant);
	}
}
