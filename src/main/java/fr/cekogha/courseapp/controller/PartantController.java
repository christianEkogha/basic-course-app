package fr.cekogha.courseapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.cekogha.courseapp.dto.PartantDTO;
import fr.cekogha.courseapp.service.PartantService;
import fr.cekogha.courseapp.service.ProducerService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Partant controller.
 */
@RestController
@RequestMapping(value = "/api/partants", produces = MediaType.APPLICATION_JSON_VALUE)
@Log4j2
public class PartantController {

	private final String ACCEPTED_MSG = "Request Accepted";
	
	private final PartantService partantService;
	private final ProducerService producerService;
	
	private final ObjectMapper mapper;
	
	/**
     * Instantiates a new Partant controller.
     *
     * @param partantService the partant service
     * @param producerService the producer service
     * @param mapper the mapper
     */
public PartantController(final PartantService partantService, final ProducerService producerService, final ObjectMapper mapper) {
		this.partantService = partantService;
		this.producerService = producerService;
		this.mapper = mapper;
		}
	
	/**
     * Gets all partants.
     *
     * @return the all partants
     * @throws JsonProcessingException the json processing exception
     */
@GetMapping
	public ResponseEntity<String> getAllPartants() throws JsonProcessingException{
		List<PartantDTO> partants = partantService.findAll();
		log.info("Partant list : {}", partants);
		producerService.sendMessage(String.valueOf(mapper.writeValueAsString(partants)));
		return ResponseEntity.accepted().body(ACCEPTED_MSG);
	}
	
	/**
     * Create partant response entity.
     *
     * @param PartantDTO the partant dto
     * @return the response entity
     */
@PostMapping
	public ResponseEntity<String> createPartant(@RequestBody @Valid PartantDTO PartantDTO){
		Long partantId = partantService.createPartant(PartantDTO);
		log.info("Partant a été créée : id = {}", partantId);
		producerService.sendMessage(String.valueOf(String.valueOf(partantId)));
		return ResponseEntity.accepted().body(ACCEPTED_MSG);
	}
}
