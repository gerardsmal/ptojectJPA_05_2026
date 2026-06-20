package com.betacom.jpa.socio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.betacom.jpa.controllers.SocioController;
import com.betacom.jpa.dto.input.SocioReq;
import com.betacom.jpa.dto.output.ResponseDTO;
import com.betacom.jpa.dto.output.SocioDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SocioControllerTest {
	
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
	public void getSocio() {
		log.debug("getSocio");
		try {
			ResponseEntity<?> resp = socioC.getById(1);
			assertEquals(HttpStatus.OK, resp.getStatusCode());
			SocioDTO soc = (SocioDTO)resp.getBody();
			Assertions.assertThat(soc.getCognome()).isEqualTo("Verde");
			
			
		} catch (Exception e) {
			new AssertionError("Errore in create " + e.getMessage());
		}		
	}
	
	@Test
	@Order(3)
	public void getSocioError() {
		log.debug("getSocio");
			
		assertThrows(Exception.class, () -> socioC.getById(99));
			
	}

}
