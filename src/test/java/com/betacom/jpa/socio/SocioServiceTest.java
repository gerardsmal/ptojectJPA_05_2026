package com.betacom.jpa.socio;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.betacom.jpa.dto.input.SocioReq;
import com.betacom.jpa.dto.output.SocioDTO;
import com.betacom.jpa.services.interfaces.ISocioServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SocioServiceTest {

	@Autowired
	private ISocioServices socioS;
	
	@Test
	@Order(1)
	public void createSocioTest() {
		log.debug("Create socio");
		try {
			SocioReq req = new SocioReq();
			req.setNome("Paolo");
			req.setCognome("Verde");
			req.setCodiceFiscale("PV11111");
			req.setEmail("pg@gmail.com");
			
			socioS.create(req);
			List<SocioDTO> lS = socioS.list(null, null, null, null);
			SocioDTO socioCreated = lS.stream()
					.filter(s -> "PV11111".equals(s.getCodiceFiscale()))
					.findFirst()
					.orElseThrow(() -> new AssertionError("Socio non travato"));
			
			Assertions.assertThat(socioCreated.getCognome()).isEqualTo("Verde");
			
			req = new SocioReq();
			req.setNome("Mimmo");
			req.setCognome("Laverdura");
			req.setCodiceFiscale("ML22222");
			req.setEmail("ml@gmail.com");
			
			socioS.create(req);
			lS = socioS.list(null, null, null, null);
			socioCreated = lS.stream()
					.filter(s -> "ML22222".equals(s.getCodiceFiscale()))
					.findFirst()
					.orElseThrow(() -> new AssertionError("Socio non travato"));
			
			Assertions.assertThat(socioCreated.getCognome()).isEqualTo("Laverdura");
			
			
		} catch (Exception e) {
			new AssertionError("Errore in create " + e.getMessage());
		}
		
	}
	
	@Test
	@Order(2)
	public void createSocioError() {
		log.debug("TEST Error in create socio");
		SocioReq req = new SocioReq();
		req.setNome("Paolo");
		req.setCognome("Verde");
		req.setCodiceFiscale("PV11111");
		req.setEmail("pg@gmail.com");

		assertThrows(Exception.class, () -> socioS.create(req));
		
		
	}
	
}
