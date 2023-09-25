package fr.cekogha.coursemanager;

import fr.cekogha.coursemanager.entity.Partant;
import fr.cekogha.coursemanager.repository.PartantRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
@OpenAPIDefinition(info=@Info(title = "${application.title}", version = "${application.version}", description = "${application.description}"))
public class CourseApplication {

	@Value("${kafka.configs.defaultTopicName}")
	private String defaultTopicName;
	
	public static void main(String[]args) {
	    ConfigurableApplicationContext context = SpringApplication.run(CourseApplication.class, args);

        PartantRepository partantRepository = context.getBean(PartantRepository.class);

        List<Partant> partants = Stream.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).map(id -> {
            Partant partant = new Partant();
            partant.setNomPartant("Partant " + id);
			return partant;
        }).toList();
        
		partants = (List<Partant>) partantRepository.saveAll(partants);

	}

	@Bean
	public NewTopic courseAppTopic() {
		NewTopic topic = TopicBuilder.name(defaultTopicName).build();
		return topic;
	}
}
