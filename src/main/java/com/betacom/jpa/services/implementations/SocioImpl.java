package com.betacom.jpa.services.implementations;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betacom.jpa.dto.input.SocioReq;
import com.betacom.jpa.dto.output.SocioDTO;
import com.betacom.jpa.mapping.SocioMap;
import com.betacom.jpa.models.Abbonamento;
import com.betacom.jpa.models.Socio;
import com.betacom.jpa.repositories.IAbbonamentoRepository;
import com.betacom.jpa.repositories.ISocioRepository;
import com.betacom.jpa.services.interfaces.ISocioServices;

import exceptions.AcademyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class SocioImpl implements ISocioServices{

	private final ISocioRepository repS;
	private final IAbbonamentoRepository abbR;
	
	@Transactional
	@Override
	public void create(SocioReq req) throws Exception {
		log.debug("create {}", req);
		Socio soc = new Socio();
		if (req.getCodiceFiscale() == null)
			throw new AcademyException("Codice fiscale non caricato");
		if (repS.existsByCodiceFiscale(req.getCodiceFiscale()))
			throw new AcademyException("Codice fiscale gia esistante");			
		soc.setCodiceFiscale(req.getCodiceFiscale());
		if (req.getCognome() == null)
			throw new AcademyException("Cognome non caricato");
		soc.setCognome(req.getCognome());
		
		if (req.getNome() == null)
			throw new AcademyException("nome non caricato");	
		soc.setNome(req.getNome());
		soc.setEmail(req.getEmail());
		
		repS.save(soc);
		
	}

	@Transactional
	@Override
	public void update(SocioReq req) throws Exception {
		log.debug("update {}", req);
		if (req.getId() == null)
			throw new AcademyException("Manca l'id del socio da modificare");
		Socio soc = repS.findById(req.getId())
				.orElseThrow(() -> new AcademyException("Socio non trovato"));
		if (req.getCodiceFiscale() != null && !req.getCodiceFiscale().equalsIgnoreCase(soc.getCodiceFiscale())) {
			if (repS.existsByCodiceFiscale(req.getCodiceFiscale()))
				throw new AcademyException("Codice fiscale gia esistante");
			soc.setCodiceFiscale(req.getCodiceFiscale());
		}
		if (req.getCognome() != null)
			soc.setCognome(req.getCognome());
		if (req.getNome() != null)
			soc.setNome(req.getNome());
		if (req.getEmail() != null)
			soc.setEmail(req.getEmail());
		repS.save(soc);
	}

	
	
	@Transactional
	@Override
	public void delete(Integer id) throws Exception {
		log.debug("delete {}", id);
		Socio soc = repS.findById(id)
				.orElseThrow(() -> new AcademyException("Socio non trovato"));
		
		/*
		 * remove abbonamenti scaduti
		 */
		if (!soc.getAbbonamento().isEmpty()) {
			List<Abbonamento> scaduti = soc.getAbbonamento().stream()
			        .filter(ab -> ab.getDataIscrizione()
			                .plusMonths(ab.getDurataValidita().longValue())
			                .isBefore(LocalDate.now()))
			        .toList();
			soc.getAbbonamento().removeAll(scaduti);
			abbR.deleteAll(scaduti);
		
		}
		
		if (!soc.getAbbonamento().isEmpty())
			throw new AcademyException("Socio con abbonamenti attivi");
			
		repS.delete(soc);
		
	}

	@Override
	public List<SocioDTO> list() throws Exception {
		log.debug("list");
		List<Socio> lS = repS.findAll();
		return SocioMap.buildSocioDTOList(lS);
	}


	@Override
	public SocioDTO getById(Integer id) throws Exception {
		log.debug("getById {}", id);
		Socio soc = repS.findById(id)
				.orElseThrow(() -> new AcademyException("Socio non trovato"));
		
		return SocioMap.buildSocioDTO(soc);
	}

}
