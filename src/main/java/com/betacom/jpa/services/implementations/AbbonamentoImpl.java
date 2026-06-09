package com.betacom.jpa.services.implementations;

import org.springframework.stereotype.Service;

import com.betacom.jpa.dto.input.AbbonamentoReq;
import com.betacom.jpa.models.Abbonamento;
import com.betacom.jpa.models.Socio;
import com.betacom.jpa.repositories.IAbbonamentoRepository;
import com.betacom.jpa.repositories.ISocioRepository;
import com.betacom.jpa.services.interfaces.IAbbonamentoServices;
import com.betacom.jpa.utils.Utilities;

import exceptions.AcademyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RequiredArgsConstructor
@Slf4j
@Service
public class AbbonamentoImpl implements IAbbonamentoServices{
	
	private final IAbbonamentoRepository repA;
	private final ISocioRepository       repS;
	

	@Override
	public void create(AbbonamentoReq req) throws Exception {
		log.debug("create :{}", req);
		if (req.getSocioId() == null)
			throw new AcademyException("Manca l'id socio per l'abbonamento");
		Socio soc = repS.findById(req.getSocioId())
				.orElseThrow(() -> new AcademyException("Socio non trovato"));
		Abbonamento abb = new Abbonamento();
	
		try {
			abb.setDataIscrizione(Utilities.stringToDate(req.getDataIscrizione()));			
		} catch (Exception e) {
			throw new AcademyException(e.getMessage());
		}
		if (req.getDurataValidita() == null) req.setDurataValidita(6);
		abb.setDurataValidita(req.getDurataValidita());
		
		abb.setSocio(soc);
		repA.save(abb);
	}

	@Override
	public void update(AbbonamentoReq req) throws Exception {
		log.debug("update :{}", req);
		if (req.getSocioId() == null)
			throw new AcademyException("Manca l'id socio per l'abbonamento");
		Socio soc = repS.findById(req.getSocioId())
				.orElseThrow(() -> new AcademyException("Socio non trovato"));
		
		Abbonamento abb = soc.getAbbonamento().stream()
		        .filter(a -> a.getId().equals(req.getId()))
		        .findFirst()
		        .orElseThrow(() -> new AcademyException("Abbonamento non trovato per il socio"));
		
		try {
			abb.setDataIscrizione(Utilities.stringToDate(req.getDataIscrizione()));			
		} catch (Exception e) {
			throw new AcademyException(e.getMessage());
		}
		if (req.getDurataValidita() != null)
			abb.setDurataValidita(req.getDurataValidita());
		
		abb.setSocio(soc);
		repA.save(abb);
		
	}

	@Override
	public void delete(Integer id, Integer socioId) throws Exception {
		log.debug("delete abbonamento : {} , socioId : {}", id, socioId);

		Socio soc = repS.findById(socioId)
				.orElseThrow(() -> new AcademyException("Socio non trovato"));

		Abbonamento abb = soc.getAbbonamento().stream()
		        .filter(a -> a.getId().equals(id))
		        .findFirst()
		        .orElseThrow(() -> new AcademyException("Abbonamento non trovato per il socio"));

		repA.delete(abb);
		
	}

}
