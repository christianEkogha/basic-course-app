package fr.cekogha.courseapp.service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProducerService {

	private final KafkaProducer<String, String> kafkaProducer;
	
	@Value("${kafka.configs.defaultTopicName}")
	private String topicName;

	public ProducerService(final KafkaProducer<String, String> kafkaProducer) {
		this.kafkaProducer = kafkaProducer;
	}
	
	public void sendMessage(final String message) {
		kafkaProducer.send(
				new ProducerRecord<>(topicName, message), 
				(result, exc) -> {
					if(exc != null)
						log.error("Unable to send the message = [{}] due to  cause = {}, trace = {}", message, exc.getMessage(), exc.getMessage());

					log.info("Sent message = [{}] with offset = [{}]", message, result.offset());
				});
		log.info("message sent : {}", message);
	}
}
