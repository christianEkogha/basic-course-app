package fr.cekogha.coursemanager.service;

import fr.cekogha.coursemanager.EmbeddedKafkaTestConfig;
import fr.cekogha.coursemanager.KafkaTestConsumer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProducerServiceTest extends EmbeddedKafkaTestConfig {

	@Autowired
	private ProducerService producerService;

    @Autowired
    private KafkaTestConsumer consumer;

	@Autowired
	EmbeddedKafkaBroker broker;

	@Test
	void givenContent_whenSendMessage_thenMessageSent() throws InterruptedException {
		producerService.sendMessage("message produced");
		boolean messageConsumed = consumer.getLatch().await(3, TimeUnit.SECONDS);
		assertTrue(messageConsumed);
		assertThat(consumer.getPayload(), containsString("message produced"));

		broker.destroy();
	}
}
