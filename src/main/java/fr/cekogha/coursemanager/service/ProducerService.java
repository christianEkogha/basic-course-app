package fr.cekogha.coursemanager.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import fr.cekogha.coursemanager.exception.NotSentException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProducerService {

	private final KafkaTemplate<String, String> kafkaTemplate;

	@Value("${kafka.configs.defaultTopicName}")
	private String topicName;

	public ProducerService(final KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendMessage(final String message) {
		try {
			kafkaTemplate.send(topicName, message);
			log.info("message sent : {}", message);
		} catch (NotSentException exception) {
			throw new NotSentException(String.format("Impossible d'envoyer le message = [%s] cause = %s, trace = %s", message, exception.getCause(), exception.getMessage()));

		}
	}
}
