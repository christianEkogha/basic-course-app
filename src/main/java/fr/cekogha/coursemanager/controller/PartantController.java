package fr.cekogha.coursemanager.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.cekogha.coursemanager.entity.Partant;
import fr.cekogha.coursemanager.service.PartantService;
import fr.cekogha.coursemanager.service.ProducerService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = "/api/partants", produces = MediaType.APPLICATION_JSON_VALUE)
@Log4j2
public class PartantController {

	private final String RETURN_MSG = "Request succeed";

	private final PartantService partantService;
	private final ProducerService producerService;

	private final ObjectMapper mapper;

	public PartantController(final PartantService partantService, final ProducerService producerService,
			final ObjectMapper mapper) {
		this.partantService = partantService;
		this.producerService = producerService;
		this.mapper = mapper;
	}

	@Operation(summary = "Get all partants")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Partant list has been retrieved successfully")
	})
	@GetMapping
	public ResponseEntity<String> getAllPartants() throws JsonProcessingException {
			List<Partant> partants = partantService.findAll();
		log.info("Partant has been created : id = {}", partants);
		producerService.sendMessage(mapper.writeValueAsString(partants));
		return ResponseEntity.ok(RETURN_MSG);
	}

	@Operation(summary = "create a partant")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Partant has been created successfully")
	})
	@PostMapping
	public ResponseEntity<String> creerPartant(@RequestParam("name") @Valid String nom) throws JsonProcessingException {
		Partant newPartant = partantService.createPartant(nom);
		log.info("Partant has been created : id = {}", newPartant);
		producerService.sendMessage(mapper.writeValueAsString(newPartant));
		return ResponseEntity.ok(RETURN_MSG);
	}
}
