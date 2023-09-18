package fr.cekogha.courseapp.service;

import fr.cekogha.courseapp.exception.NotSentException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    public ProducerService(final KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(final String message) {
        try {
            kafkaTemplate.send(topicName, message);
            log.info("message envoyé = [{}]", message);
        } catch (Exception exception) {
            throw new NotSentException(String.format("Impossible d'envoyer le message = [%s] cause = %s, trace = %s", message, exception.getCause(), exception.getMessage()));
        }
    }
}
