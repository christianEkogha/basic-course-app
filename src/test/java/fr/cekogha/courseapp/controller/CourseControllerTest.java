package fr.cekogha.courseapp.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
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
	@DisplayName("Avec aucun argument - get all Course - accepted")
	void givenNoArgs_whenGetAllPartants_thenReturnIsAccepted() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/courses"))
		.andExpect(MockMvcResultMatchers.status().isAccepted());
	}
	
	@Test
	@DisplayName("Avec course dto with 2 on 3 existing partants - create course - not found")
	void givenACourseWith2on3ExistingPart_whenCreateCourse_thenReturnNotFound() throws Exception {
		String bodyCourseDTO = """
				{
					"numero" : 979,
					"nom" : "course-create-course-is-notfound",
					"jour" : "2024-02-15",
					"partantIds" : [123, 127, 106]
				}
				""";
		mockMvc.perform(MockMvcRequestBuilders.post("/api/courses").content(bodyCourseDTO).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	@DisplayName("Avec course dto with no existing partants - create course - not found")
	void givenACourseWithNoExistingPart_whenCreateCourse_thenReturnNotFound() throws Exception {
		String bodyCourseDTO = """
				{
					"numero" : 980,
					"nom" : "course-create-course-is-notfound",
					"jour" : "2024-02-15",
					"partantIds" : [103, 107, 106]
				}
				""";
		mockMvc.perform(MockMvcRequestBuilders.post("/api/courses").content(bodyCourseDTO).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	@DisplayName("Avec course dto valid - create course - is Accepted")
	void givenACourseDTOValid_whenCreateCourse_thenReturnIsAccepted() throws Exception {
		String bodyCourseDTO = """
				{
					"numero" : 979,
					"nom" : "course-create-course-is-accepted",
					"jour" : "2024-02-15",
					"partantIds" : [123, 127, 126]
				}
				""";
		mockMvc.perform(MockMvcRequestBuilders.post("/api/courses").content(bodyCourseDTO).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isAccepted());
	}

}
