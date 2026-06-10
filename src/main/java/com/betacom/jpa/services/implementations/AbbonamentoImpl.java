package com.betacom.jpa.services.implementations;

import org.springframework.stereotype.Service;

import com.betacom.jpa.dto.input.AbbonamentoReq;
import com.betacom.jpa.dto.output.AbbonamentoDTO;
import com.betacom.jpa.mapping.AbbonamentoMap;
import com.betacom.jpa.models.Abbonamento;
import com.betacom.jpa.models.Attivita;
import com.betacom.jpa.models.Socio;
import com.betacom.jpa.repositories.IAbbonamentoRepository;
import com.betacom.jpa.repositories.IAttivitaRepository;
import com.betacom.jpa.repositories.ISocioRepository;
import com.betacom.jpa.services.interfaces.IAbbonamentoServices;
import com.betacom.jpa.utils.Utilities;

import exceptions.AcademyException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RequiredArgsConstructor
@Slf4j
@Service
public class AbbonamentoImpl implements IAbbonamentoServices{
	
	private final IAbbonamentoRepository repA;
	private final ISocioRepository       repS;
	private final IAttivitaRepository    repAttiv;
	
	@Transactional
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

	@Transactional
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
	@Transactional
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

	@Transactional
	@Override
	public void createAbbonamentoAttivita(AbbonamentoReq req) throws Exception {
		log.debug("createAbbonamentoAttivita {}", req);
		
		Abbonamento abb = repA.findById(req.getId())
				.orElseThrow(() -> new AcademyException("Abbonamento non trovato"));
		
		Attivita attiv = repAttiv.findById(req.getAttivitaId())
				.orElseThrow(() -> new AcademyException("Attivita non trovata"));
		
		if (abb.getAttivita().contains(attiv))
			throw new AcademyException("Attivita presente per l'abbobamento");
		abb.getAttivita().add(attiv);
		repA.save(abb);
		
	}

	
	@Override
	public AbbonamentoDTO getAbbonamentoById(Integer id) throws Exception {
		log.debug("getAbbonamentoBtId {}", id);
		Abbonamento abb = repA.findById(id)
				.orElseThrow(() -> new AcademyException("Abbonamento non trovato"));

		return AbbonamentoMap.buildAbbonamentoDTO(abb);
	}

	@Override
	public void deleteAbbonamentoAttivita(Integer id, Integer attivitaId) throws Exception {
		log.debug("deleteAbbonamentoAttivita id abbonamento {} idAttivita {}", id, attivitaId);
		Abbonamento abb = repA.findById(id)
				.orElseThrow(() -> new AcademyException("Abbonamento non trovato"));
		
		abb.getAttivita().stream()
			.filter(at -> at.getId().equals(attivitaId))
			.findFirst()
			.ifPresent(abb.getAttivita() :: remove);

		repA.save(abb);		
	}
}