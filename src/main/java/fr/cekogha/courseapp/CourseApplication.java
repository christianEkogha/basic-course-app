package fr.cekogha.courseapp;

import fr.cekogha.courseapp.entity.Course;
import fr.cekogha.courseapp.entity.Partant;
import fr.cekogha.courseapp.repository.CourseRepository;
import fr.cekogha.courseapp.repository.PartantRepository;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

/**
 * The type Course application.
 */
@SpringBootApplication
public class CourseApplication {

    @Value("${spring.kafka.topic.name}")
    private String defaultTopicName;
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(CourseApplication.class, args);

        PartantRepository partantRepository = context.getBean(PartantRepository.class);
        CourseRepository courseRepository = context.getBean(CourseRepository.class);

        Set<Partant> partants = Stream.of(100L, 102L, 106L, 1651351L, 156L).map(id -> {
            Partant partant = new Partant();
            partant.setId(id);
            partant.setNom("Partant " + id);
			return partant;
        }).collect(Collectors.toSet());
		partantRepository.saveAll(partants);

		Course course = new Course();
		course.setId(0L);
		course.setNom("Course 0");
		course.setJour(LocalDate.now());
		course.setPartants(partants);
        courseRepository.save(course);
    }

    /**
     * Course app topic new topic.
     *
     * @return the new topic
     */
@Bean
    public NewTopic courseAppTopic() {
        NewTopic topic = TopicBuilder.name(defaultTopicName).build();
        return topic;
    }
}
