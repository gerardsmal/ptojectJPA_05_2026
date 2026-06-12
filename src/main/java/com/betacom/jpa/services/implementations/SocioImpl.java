package com.betacom.jpa.services.implementations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betacom.jpa.dto.input.SocioReq;
import com.betacom.jpa.dto.output.SocioDTO;
import com.betacom.jpa.mapping.SocioMap;
import com.betacom.jpa.models.Abbonamento;
import com.betacom.jpa.models.Socio;
import com.betacom.jpa.repositories.IAbbonamentoRepository;
import com.betacom.jpa.repositories.ISocioRepository;
import com.betacom.jpa.services.interfaces.IMessaggioServices;
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
			throw new AcademyException("socio.cfisc.invalid");
		if (repS.existsByCodiceFiscale(req.getCodiceFiscale()))
			throw new AcademyException("socio.cfisc.exist");			
		soc.setCodiceFiscale(req.getCodiceFiscale());
		
		soc.setCognome(Optional.ofNullable(req.getCognome())
				.orElseThrow(() -> new AcademyException("socio.no.cognome")));
		
		soc.setNome(Optional.ofNullable(req.getNome())
				.orElseThrow(() -> new AcademyException("socio.no.nome")));
		
		soc.setEmail(req.getEmail());
		
		repS.save(soc);
		
	}

	@Transactional
	@Override
	public void update(SocioReq req) throws Exception {
		log.debug("update {}", req);
		if (req.getId() == null)
			throw new AcademyException("socio.no.id");
		Socio soc = repS.findById(req.getId())
				.orElseThrow(() -> new AcademyException("socio.ntfnd"));
		if (req.getCodiceFiscale() != null && !req.getCodiceFiscale().equalsIgnoreCase(soc.getCodiceFiscale())) {
			if (repS.existsByCodiceFiscale(req.getCodiceFiscale()))
				throw new AcademyException("socio.cfisc.exist");
			soc.setCodiceFiscale(req.getCodiceFiscale());
		}
		
		Optional.ofNullable(req.getCognome()).ifPresent(soc::setCognome);
		Optional.ofNullable(req.getNome()).ifPresent(soc::setNome);
		Optional.ofNullable(req.getEmail()).ifPresent(soc::setEmail);

	}

	
	
	@Transactional
	@Override
	public void delete(Integer id) throws Exception {
		log.debug("delete {}", id);		
		Socio soc = repS.findById(id)
				.orElseThrow(() -> new AcademyException("socio.ntfnd"));
		
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
			throw new AcademyException("socio.invalid.delete");
			
		repS.delete(soc);
		
	}

	@Override
	public List<SocioDTO> list (Integer id, String nome, String cognome, Integer attivita) throws Exception {
		log.debug("list :{} / {} / {} / {}", id, nome, cognome, attivita);
//		List<Socio> ss = repS.searchByCognome("A");
//		List<Socio> ss = repS.findByCognomeStartingWith("A");
//			ss.forEach(s -> log.debug("prova: {}", s.toString()));
//		
		
		List<Socio> lS = repS.searchByFilter(id, nome, cognome, attivita);
		return SocioMap.buildSocioDTOList(lS);
	}


	@Override
	public SocioDTO getById(Integer id) throws Exception {
		log.debug("getById {}", id);
		Socio soc = repS.findById(id)
				.orElseThrow(() -> new AcademyException("socio.ntfnd"));
		
		return SocioMap.buildSocioDTO(soc);
	}

	@Override
	public List<SocioDTO> listByAttivita(Integer id) throws Exception {
		log.debug("listByAttivita {}", id);
		List<Socio> lS = repS.selectByAttivita(id);
		return SocioMap.buildSocioDTOList(lS);
	}

}
