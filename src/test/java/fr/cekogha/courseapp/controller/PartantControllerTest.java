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
class PartantControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	PartantController controller;

	@Test
	void initControllerTest() throws Exception {
		assertThat(controller).isNotNull();
	}
	
	@Test
	@DisplayName("Avec aucun argument - get all Partants - accepted")
	void givenNoArgs_whenGetAllPartants_thenReturnIsAccepted() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/Partants"))
		.andExpect(MockMvcResultMatchers.status().isAccepted());
	}
	
	@Test
	@DisplayName("Avec Partant dto with id null - create Partant - bad request")
	void givenAPartantWithId_whenCreatePartant_thenReturnBadRequest() throws Exception {
		String bodyPartantDTO = """
				{
					"nom" : "create-Partant-is-badrequest",
				}
				""";
		mockMvc.perform(MockMvcRequestBuilders.post("/api/partants").content(bodyPartantDTO).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	@DisplayName("Avec Partant dto valid - create Partant - accepted")
	void givenPartantDTOValid_whenCreatePartant_thenReturnIsAccepted() throws Exception {
		String bodyPartantDTO = """
				{
					"nom" : "create-Partant-is-accepted"
				}
				""";
		mockMvc.perform(MockMvcRequestBuilders.post("/api/partants").content(bodyPartantDTO).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isAccepted());
	}

}
