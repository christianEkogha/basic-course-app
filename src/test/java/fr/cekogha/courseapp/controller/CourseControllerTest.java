package fr.cekogha.courseapp.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import fr.cekogha.coursemanager.controller.CourseController;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc()
@ActiveProfiles("test")
class CourseControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	CourseController controller;

	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;

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
	@DisplayName("Avec course dto with 2 on 3 existing partants - create course - not found")
	void givenACourseWith2on3ExistingPart_whenCreateCourse_thenReturnNotFound() throws Exception {
		String bodyCourseDTO = """
					{
				    "nomCourse": "course-test-102",
				    "jourCourse": "2024-02-16",
				    "positions": [
				        {
				            "partant": {
				                "idPartant": 3
				            },
				            "numero": 2
				        },
				        {
				            "partant": {
				                "idPartant": 2
				            },
				            "numero": 3
				        },
				        {
				            "partant": {
				                "idPartant": 2
				            },
				            "numero": 3
				        }
				    ]
				}
								""";
		;
		mockMvc.perform(MockMvcRequestBuilders.post("/api/courses").content(bodyCourseDTO)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	@DisplayName("Avec course dto with no existing partants - create course - not found")
	void givenACourseWithNoExistingPart_whenCreateCourse_thenReturnNotFound() throws Exception {
		String bodyCourseDTO = """
					{
				    "nomCourse": "course-test-102",
				    "jourCourse": "2024-02-16",
				    "positions": [
				        {
				            "partant": {
				                "idPartant": 3
				            },
				            "numero": 1
				        },
				        {
				            "partant": {
				                "idPartant": 2
				            },
				            "numero": 2
				        },
				        {
				            "partant": {
				                "idPartant": 2
				            },
				            "numero": 3
				        }
				    ]
				}
								""";
		;
		mockMvc.perform(MockMvcRequestBuilders.post("/api/courses").content(bodyCourseDTO)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	@DisplayName("Avec course dto with less than 3 partants - create course - bad request")
	void givenACourseWithLessThan3Part_whenCreateCourse_thenReturnNotFound() throws Exception {
		String bodyCourseDTO = """
					{
				    "nomCourse": "course-test-102",
				    "jourCourse": "2024-02-16",
				    "positions": [
				        {
				            "partant": {
				                "idPartant": 3
				            },
				            "numero": 1
				        },
				        {
				            "partant": {
				                "idPartant": 2
				            },
				            "numero": 2
				        }
				    ]
				}
								""";
		;
		mockMvc.perform(MockMvcRequestBuilders.post("/api/courses").content(bodyCourseDTO)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	@DisplayName("Avec course dto valid - create course - ok")
	void givenACourseDTOValid_whenCreateCourse_thenReturnIsOk() throws Exception {
		String bodyCourseDTO = """
				{
				    "nomCourse": "course-test-102",
				    "jourCourse": "2024-02-16",
				    "positions": [
				        {
				            "partant": {
				                "idPartant": 123
				            },
				            "numero": 1
				        },
				        {
				            "partant": {
				                "idPartant": 124
				            },
				            "numero": 2
				        },
				        {
				            "partant": {
				                "idPartant": 125
				            },
				            "numero": 3
				        }
				    ]
				}
								""";
		mockMvc.perform(MockMvcRequestBuilders.post("/api/courses").content(bodyCourseDTO)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
	}

}
