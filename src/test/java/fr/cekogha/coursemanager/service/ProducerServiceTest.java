package fr.cekogha.coursemanager.service;

import fr.cekogha.coursemanager.KafkaTestConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@EmbeddedKafka(brokerProperties = {"listeners=PLAINTEXT://localhost:9094"}, partitions = 1)
@ActiveProfiles("test")
class ProducerServiceTest {

	@Autowired
	private ProducerService producerService;

    @Autowired
    private KafkaTestConsumer consumer;

	@Test
	void givenContent_whenSendMessage_thenMessageSent() throws InterruptedException {
		
		producerService.sendMessage("message produced");

		boolean messageConsumed = consumer.getLatch().await(3, TimeUnit.SECONDS);
		assertTrue(messageConsumed);
		assertThat(consumer.getPayload(), containsString("message produced"));
	}
}
