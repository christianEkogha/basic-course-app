package fr.cekogha.courseapp;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CourseApplicationTest {

	@Autowired
	CourseApplication application;
	
	@Test
	void test() {
		assertThat(application).isNotNull();
	}

}
