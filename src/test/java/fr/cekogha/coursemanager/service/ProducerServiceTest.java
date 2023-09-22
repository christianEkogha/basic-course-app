package fr.cekogha.coursemanager.service;

import java.time.Duration;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@RunWith(SpringRunner.class)
@DirtiesContext
@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
class ProducerServiceTest {

	@Container
	static final KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"));

	@DynamicPropertySource
	static void overrideProperties(DynamicPropertyRegistry registry) {
		System.out.println("!!!!!!!!!!!!!!! " + kafka.getBootstrapServers());
		registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
	}

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	private ProducerService producerService;

    @Autowired
    private KafkaConsumer<String, String> consumer;

	@Test
	void givenContent_whenSendMessage_thenMessageSent() {
		
		producerService.sendMessage("message produced");

        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMinutes(2));
        Assertions.assertNotNull(records);
        records.forEach(rcd -> {
            Assertions.assertTrue(rcd.value().contains("message produced"));
        });
	}
}
