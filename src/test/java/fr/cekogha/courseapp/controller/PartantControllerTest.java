package fr.cekogha.courseapp.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import fr.cekogha.coursemanager.controller.PartantController;

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
	@DisplayName("Avec aucun argument - get all Partants - ok")
	void givenNoArgs_whenGetAllPartants_thenReturnIsOk() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/Partants"))
		.andExpect(MockMvcResultMatchers.status().isAccepted());
	}
	
	@Test
	@DisplayName("Avec Partant dto with nom null - create Partant - bad request")
	void givenAPartantWithId_whenCreatePartant_thenReturnBadRequest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/partants"))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	@DisplayName("Avec Partant dto valid - create Partant - ok")
	void givenPartantDTOValid_whenCreatePartant_thenReturnIsOk() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/partants").param("name", "fred"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
