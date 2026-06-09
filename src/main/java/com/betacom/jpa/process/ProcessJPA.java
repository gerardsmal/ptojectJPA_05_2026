package com.betacom.jpa.process;

import java.util.List;

import org.springframework.stereotype.Component;

import com.betacom.jpa.dto.input.CertificatoReq;
import com.betacom.jpa.dto.input.SocioReq;
import com.betacom.jpa.dto.output.SocioDTO;
import com.betacom.jpa.services.interfaces.ICertificatoServices;
import com.betacom.jpa.services.interfaces.ISocioServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Component
public class ProcessJPA {

	private final ISocioServices socS;
	private final ICertificatoServices certS;
	
	
	public void execute() {
		try {
//			int socioId = insertSocio( SocioReq.builder()
//					.cognome("Blu")
//					.nome("Anna")
//					.codiceFiscale("333333")
//					.email("ccc@tin.it")
//					.build(), "02/06/2026");
//
//			int socioId1 = insertSocio( SocioReq.builder()
//					.cognome("Giallo")
//					.nome("Paolo")
//					.codiceFiscale("444444")
//					.email("ddd@tin.it")
//					.build(), "04/05/2026");
//			
//			log.debug("remove socio {}", socioId);
//			socS.delete(socioId);
			
//			listSocio();
//			updateSocio(SocioReq.builder()
//					.id(10)
//					.codiceFiscale("zzz99999")
//					.build()
//					);
			
			/*
			 * update certificato medico
			 */
//			certS.update(CertificatoReq.builder()
//					.socioId(10)
//					.dataCertificato("01/06/2026")
//					.tipo(true)
//					.build()
//					);
			/*
			 * insert new socio
			 */
//			int socioId = insertSocio( SocioReq.builder()
//					.cognome("Argento")
//					.nome("Mirko")
//					.codiceFiscale("qwdbdiwjk")
//					.email("assd@tin.it")
//					.build(), "05/06/2026");
			
//			log.debug("socio id:3 :{}", socS.getById(3));
			
			
					
		} catch (Exception e) {
			log.error("error found {}", e.getMessage());
		}
		
	}
	
//	private int insertSocio(SocioReq req, String dataCer) throws Exception{
//		int socioId = socS.create(req);
//		CertificatoReq reqCert = CertificatoReq.builder()
//				.dataCertificato(dataCer)
//				.socioId(socioId)
//				.build();
//		certS.update(reqCert);
//		return socioId;
//	
//	}
//
//	private void updateSocio(SocioReq req) throws Exception{
//		socS.update(req);
//	}
//
//	
//	
//	private void listSocio() throws Exception {
//		List<SocioDTO> lS = socS.list();
//		lS.forEach(s -> log.debug(s.toString()));
//	}
}
