package com.betacom.jpa.socio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.betacom.jpa.controllers.SocioController;
import com.betacom.jpa.dto.input.SocioReq;
import com.betacom.jpa.dto.output.ResponseDTO;
import com.betacom.jpa.dto.output.SocioDTO;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SocioControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private SocioController socioC;
	
	
	@Test
	@Order(1)
	public void createSocio() {
		log.debug("test controller socio");
		
		SocioReq req = new SocioReq();
		req.setNome("Anna");
		req.setCognome("Labella");
		req.setCodiceFiscale("AB33333");
		req.setEmail("ab@gmail.com");
		
		try {
			ResponseEntity<ResponseDTO> resp = socioC.create(req);
			assertEquals(HttpStatus.OK, resp.getStatusCode());
			
		} catch (Exception e) {
			new AssertionError("Errore in create " + e.getMessage());
		}
		
	}
	
	@Test
	@Order(2)
	public void getSocio() throws Exception{
		log.debug("getSocio");
		ResponseEntity<?> resp = socioC.getById(1);
		assertEquals(HttpStatus.OK, resp.getStatusCode());
		SocioDTO soc = (SocioDTO)resp.getBody();
		Assertions.assertThat(soc.getCognome()).isEqualTo("Verde");			
	}
	
	@Test
	@Order(3)
	public void getSocioError() throws Exception{
		log.debug("getSocio");
			
		mockMvc.perform(get("/rest/socio/getById")
		            .param("id", "99"))
		        .andExpect(status().isBadRequest())
		        .andExpect(jsonPath("$.msg").exists()); // per verficare che il return contain il tag msg
			
	}
	
	@Test
	@Order(4)
	public void createSocioController() throws Exception{
		log.debug("createSocioController");

		SocioReq req = new SocioReq();
		req.setNome("Antonio");
		req.setCognome("Prise");
		req.setCodiceFiscale("PA2324242");
		req.setEmail("ap@gmail.com");

		mockMvc.perform(post("/rest/socio/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req))
				).andExpect(status().isOk());
		
		
	}

	@Test
	@Order(5)
	public void updateSocioController() throws Exception{
		log.debug("updateSocioController");

		SocioReq req = new SocioReq();
		req.setId(4);
		req.setCodiceFiscale("PA22333KE");

		mockMvc.perform(patch("/rest/socio/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req))
				).andExpect(status().isOk());
		
		
	}

	
	@Test
	@Order(6)
	public void listAllController() throws Exception {
		log.debug("listAll");

		MvcResult result = mockMvc.perform(get("/rest/socio/list"))
	            .andExpect(status().isOk())
	            .andReturn();
		  
		String json = result.getResponse().getContentAsString();
		
		List<SocioDTO> lS = objectMapper.readValue(
	            json,
	            new TypeReference<List<SocioDTO>>() {}
	    );
		
		assertFalse(lS.isEmpty());
		
		lS.forEach(s -> log.debug(s.toString()));
	}

	@Test
	@Order(7)
	public void createSocioControllerError() throws Exception{
		log.debug("createSocioControllerError");

		SocioReq req = new SocioReq();
		req.setNome("Antonio");
		req.setCodiceFiscale("PA2324242");
		req.setEmail("ap@gmail.com");

		mockMvc.perform(post("/rest/socio/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req))
				)
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.msg").exists());		
		
		
	}

}
