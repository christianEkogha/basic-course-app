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

    public void sendMessage(final String eventDto) {
        try {
            kafkaTemplate.send(topicName, eventDto).handle((result, exception) -> {
                if (exception != null)
                    return exception;

                log.info("message sent : {}", eventDto);
                return eventDto;
            });
        } catch (Exception exception) {
            throw new NotSentException(String.format("Impossible d'envoyer le message = [%s] cause = %s, trace = %s", eventDto, exception.getCause(), exception.getMessage()));
        }
    }
}
