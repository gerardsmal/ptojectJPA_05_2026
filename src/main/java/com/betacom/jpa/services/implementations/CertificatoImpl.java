package com.betacom.jpa.services.implementations;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betacom.jpa.dto.input.CertificatoReq;
import com.betacom.jpa.models.Certificato;
import com.betacom.jpa.models.Socio;
import com.betacom.jpa.repositories.ICertificatoRepositoy;
import com.betacom.jpa.repositories.ISocioRepository;
import com.betacom.jpa.services.interfaces.ICertificatoServices;
import com.betacom.jpa.utils.Utilities;

import exceptions.AcademyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CertificatoImpl implements ICertificatoServices{
	private final ICertificatoRepositoy repC;
	private final ISocioRepository      repS;
	
	@Transactional
	@Override
	public void update(CertificatoReq req) throws Exception {
		log.debug("create {}", req);
		Socio soc = repS.findById(req.getSocioId())
				.orElseThrow(() -> new AcademyException("Socio non trovato"));
		Certificato cer = null;
		if (soc.getCertificato() == null) {
			log.debug("new certificato per {}", soc.getId());
			cer = new Certificato();
		} else
			cer = soc.getCertificato();
		
		try {
			cer.setDataCertificato(Utilities.stringToDate(req.getDataCertificato()));			
		} catch (Exception e) {
			throw new AcademyException(e.getMessage());
		}
		cer.setTipo(req.getTipo() == null ? false : req.getTipo());
		
		soc.setCertificato(repC.save(cer));
	}

}
