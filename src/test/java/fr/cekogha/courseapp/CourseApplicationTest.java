package fr.cekogha.courseapp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/** The type Course application test. */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CourseApplicationTest {

  @Autowired private CourseApplication application;

  /** Test the spring context initialization */
  @Test
  void test() {
    assertThat(application).isNotNull();
  }
}
