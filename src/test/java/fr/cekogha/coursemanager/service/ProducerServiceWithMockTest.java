package fr.cekogha.coursemanager.service;

import fr.cekogha.coursemanager.EmbeddedKafkaTestConfig;
import fr.cekogha.coursemanager.exception.NotSentException;
import org.apache.kafka.common.errors.TimeoutException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

class ProducerServiceWithMockTest extends EmbeddedKafkaTestConfig {

	@Mock
	KafkaTemplate<String, String> mockKafkaTemplate;

	@InjectMocks
	private ProducerService producerService;

	@Autowired
	EmbeddedKafkaBroker broker;

	@Test
	void givenKafkaTemplateSendFailed_whenSendMessage_thenNotSentException() {

		Mockito.when(mockKafkaTemplate.send(Mockito.anyString(), Mockito.anyString())).thenThrow(TimeoutException.class);

		Assertions.assertThatThrownBy(() -> producerService.sendMessage("message produced"))
				.isInstanceOf(NotSentException.class);
		broker.destroy();
	}
}
