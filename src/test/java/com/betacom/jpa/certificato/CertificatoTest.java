package com.betacom.jpa.certificato;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.betacom.jpa.dto.input.CertificatoReq;
import com.betacom.jpa.dto.output.SocioDTO;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CertificatoTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	
	@Test
	@Order(1)
	public void createCertificatoTest() throws Exception {
		log.debug("createCertificatoTest");
		
		CertificatoReq req = new CertificatoReq();
		req.setSocioId(4);
		req.setDataCertificato("01/06/2026");
		req.setTipo(true);
		
		mockMvc.perform(put("/rest/certificato/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req))
				).andExpect(status().isOk());

	}
	
	@Test
	@Order(2)
	public void controlCertificatoTest() throws Exception{
		log.debug("controlCertificatoTest");
		MvcResult result = mockMvc.perform(get("/rest/socio/getById")
				.param("id", "4"))			
	            .andExpect(status().isOk())
	            .andReturn();
		  
		String json = result.getResponse().getContentAsString();

		SocioDTO soc = objectMapper.readValue(json, SocioDTO.class);
		
		log.debug("socio 4 {}", soc);
	}
	
	
	
}
