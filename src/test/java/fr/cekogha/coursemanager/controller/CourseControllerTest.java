package fr.cekogha.coursemanager.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import fr.cekogha.coursemanager.controller.CourseController;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc()
@EmbeddedKafka(brokerProperties = {"listeners=PLAINTEXT://localhost:9094"}, partitions = 1)
@ActiveProfiles("test")
class CourseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CourseController controller;

    @Test
    void initControllerTest() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    @DisplayName("Avec aucun argument - get all Course - ok")
    void givenNoArgs_whenGetAllPartants_thenReturnIsOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/courses")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Avec course dto with 1 partants duplicated - create course - bad request")
    void givenACourseWith2on3ExistingPart_whenCreateCourse_thenReturnNotFound() throws Exception {
        String bodyCourseDTO = """
                {
                   "nomCourse": "course-test-102",
                   "jourCourse": "2024-02-16",
                       "partantIds": [
                          1,
                          5,
                          5
                      ]
                }""";
        ;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/courses").content(bodyCourseDTO).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Avec course dto with no existing partants - create course - not found")
    void givenACourseWithNoExistingPart_whenCreateCourse_thenReturnNotFound() throws Exception {
        String bodyCourseDTO = """
                {
                    "nomCourse": "course-test-102",
                    "jourCourse": "2024-02-16",
                    "partantIds": [
                      65165165,651655,11351351351,315
                    ]
                }""";
        ;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/courses").content(bodyCourseDTO).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Avec course dto with less than 3 partants - create course - bad request")
    void givenACourseWithLessThan3Part_whenCreateCourse_thenReturnNotFound() throws Exception {
        String bodyCourseDTO = """
                	{
                    "nomCourse": "course-test-102",
                    "jourCourse": "2024-02-16",
                  "partantIds": [
                      65165165,651655
                    ]
                }""";
        ;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/courses").content(bodyCourseDTO).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Avec course dto valid - create course - ok")
    void givenACourseDTOValid_whenCreateCourse_thenReturnIsOk() throws Exception {
        String bodyCourseDTO = """
                {
                    "nomCourse": "course-test-102",
                    "jourCourse": "2024-02-16",
                    "partantIds": [
                      123,124,126,127
                    ]
                }""";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/courses").content(bodyCourseDTO).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
    }

}
