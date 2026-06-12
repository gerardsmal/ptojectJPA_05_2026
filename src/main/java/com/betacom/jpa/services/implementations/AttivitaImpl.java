package com.betacom.jpa.services.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.betacom.jpa.dto.input.AttivitaReq;
import com.betacom.jpa.dto.output.AttivitaDTO;
import com.betacom.jpa.mapping.AttivitaMap;
import com.betacom.jpa.models.Attivita;
import com.betacom.jpa.repositories.IAttivitaRepository;
import com.betacom.jpa.services.interfaces.IAttivitaServices;

import exceptions.AcademyException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AttivitaImpl implements IAttivitaServices{

	private final IAttivitaRepository attivR;
	
	@Transactional
	@Override
	public void create(AttivitaReq req) throws Exception {
		log.debug("create {}", req);
		if (attivR.existsByDescrizione(req.getDescrizione().trim().toUpperCase()))
			throw new AcademyException("attiv.exist");
		Attivita at = new Attivita();

		at.setDescrizione(Optional.ofNullable(req.getDescrizione().trim().toUpperCase())
				.orElseThrow(() -> new AcademyException("attiv.no.desc")));

		at.setPrezzo(Optional.ofNullable(req.getPrezzo())
				.orElseThrow(() -> new AcademyException("attiv.no.price")));

		attivR.save(at);
	}

	@Transactional
	@Override
	public void update(AttivitaReq req) throws Exception {
		log.debug("update {}", req);
		Attivita at = attivR.findById(req.getId())
				.orElseThrow(() -> new AcademyException("attiv.ntfnd"));
		
		if (req.getDescrizione() != null) {
			if (attivR.existsByDescrizione(req.getDescrizione().trim().toUpperCase()))
				throw new AcademyException("attiv.exist");
			at.setDescrizione(req.getDescrizione().translateEscapes().toUpperCase());
		}
		
		Optional.ofNullable(req.getPrezzo()).ifPresent(at::setPrezzo);
		
		attivR.save(at);
	}
	@Transactional
	@Override
	public void delete(Integer id) throws Exception {
		log.debug("delete {}", id);
		Attivita at = attivR.findById(id)
				.orElseThrow(() -> new AcademyException("attiv.ntfnd"));
		if (!at.getAbbonamento().isEmpty())
			throw new AcademyException("attiv.invalid.delete");
		attivR.delete(at);
	}

	@Override
	public List<AttivitaDTO> list() throws Exception {
		List<Attivita> lA = attivR.findAll();
		return AttivitaMap.buildAttivitaDTOList(lA);
	}

	@Override
	public AttivitaDTO getById(Integer id) throws Exception {
		log.debug("getById {}", id);
		Attivita at = attivR.findById(id)
				.orElseThrow(() -> new AcademyException("Attivita non trovata"));
		return AttivitaMap.buildAttivitaDTO(at);
	}

}
