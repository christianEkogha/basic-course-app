package fr.cekogha.courseapp.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "kafka.configs")
@EnableConfigurationProperties(KafkaConfig.class)
@Data
public class KafkaConfig {

	private String bootstrapServers;

	private String defaultTopicName;

	@Bean
	public KafkaProducer<String, String> kafkaProducer() {
		return new KafkaProducer<>(producerProps());
	}

	@Bean
	public Map<String, Object> producerProps() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return props;
	}

	@Bean
	public KafkaAdmin admin() {
		return new KafkaAdmin(Map.of(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers));
	}

	@Bean
	public NewTopic courseAppTopic() {
		NewTopic topic = TopicBuilder.name(defaultTopicName).build();
		System.out.println("CONFIG!!!!! " + topic.configs());
		return topic;
	}
}
