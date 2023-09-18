package fr.cekogha.courseapp.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.cekogha.courseapp.dto.PartantDTO;
import fr.cekogha.courseapp.service.PartantService;
import fr.cekogha.courseapp.service.ProducerService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = "/api/partants", produces = MediaType.APPLICATION_JSON_VALUE)
@Log4j2
public class PartantController {

	private final String ACCEPTED_MSG = "Request Accepted";
	
	private final PartantService partantService;
	private final ProducerService producerService;
	
	private final ObjectMapper mapper;
	
	public PartantController(final PartantService partantService, final ProducerService producerService, final ObjectMapper mapper) {
		this.partantService = partantService;
		this.producerService = producerService;
		this.mapper = mapper;
		}
	
	@GetMapping
	public ResponseEntity<String> getAllPartants() throws JsonProcessingException{
		List<PartantDTO> partants = partantService.findAll();
		log.info("Partant a été créée : id = {}", partants);
		producerService.sendMessage(String.valueOf(mapper.writeValueAsString(partants)));
		return ResponseEntity.accepted().body(ACCEPTED_MSG);
	}
	
	@PostMapping
	public ResponseEntity<String> createPartant(@RequestBody @Valid PartantDTO PartantDTO){
		Long partantId = partantService.createPartant(PartantDTO);
		log.info("Partant a été créée : id = {}", partantId);
		producerService.sendMessage(String.valueOf(String.valueOf(partantId)));
		return ResponseEntity.accepted().body(ACCEPTED_MSG);
	}
}
