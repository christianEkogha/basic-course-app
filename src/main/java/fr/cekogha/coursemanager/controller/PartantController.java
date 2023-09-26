package fr.cekogha.coursemanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.cekogha.coursemanager.dto.EventDTO;
import fr.cekogha.coursemanager.dto.EventType;
import fr.cekogha.coursemanager.entity.Partant;
import fr.cekogha.coursemanager.service.PartantService;
import fr.cekogha.coursemanager.service.ProducerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

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
		EventDTO eventDTO = new EventDTO(EventType.Partant, partants, LocalDateTime.now());
		producerService.sendMessage(mapper.writeValueAsString(eventDTO));
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
		EventDTO eventDTO = new EventDTO(EventType.Partant, Stream.of(newPartant).toList(), LocalDateTime.now());
		producerService.sendMessage(mapper.writeValueAsString(eventDTO));
		return ResponseEntity.ok(RETURN_MSG);
	}
}
