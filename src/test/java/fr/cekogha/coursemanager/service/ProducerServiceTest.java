package fr.cekogha.coursemanager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.cekogha.coursemanager.EmbeddedKafkaTestConfig;
import fr.cekogha.coursemanager.KafkaTestConsumer;
import fr.cekogha.coursemanager.dto.EventDTO;
import fr.cekogha.coursemanager.dto.EventType;
import fr.cekogha.coursemanager.entity.Partant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProducerServiceTest extends EmbeddedKafkaTestConfig {

	@Autowired
	private ProducerService producerService;

    @Autowired
    private KafkaTestConsumer consumer;

	@Autowired
	private ObjectMapper mapper;

	@Test
	void givenContent_whenSendMessage_thenMessageSent() throws InterruptedException, JsonProcessingException {
		Partant partant = new Partant();
		partant.setNomPartant("Boris");
		partant.setIdPartant(15L);
		partant.setPositions(new ArrayList<>());
		EventDTO eventDTO = new EventDTO(EventType.Partant, Stream.of(partant).toList(), LocalDateTime.now());
		producerService.sendMessage(mapper.writeValueAsString(eventDTO));
		boolean messageConsumed = consumer.getLatch().await(3, TimeUnit.SECONDS);
		assertTrue(messageConsumed);
		assertThat(consumer.getPayload(), containsString("\"idPartant\":15"));
		assertThat(consumer.getPayload(), containsString("\"nomPartant\":\"Boris\""));

	}
}
