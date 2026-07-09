package com.betacom.jpa.services.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.betacom.jpa.dto.input.PersoneReq;
import com.betacom.jpa.dto.output.PersoneDTO;
import com.betacom.jpa.exceptions.AcademyException;
import com.betacom.jpa.models.Personne;
import com.betacom.jpa.repositories.ICertificatoRepositoy;
import com.betacom.jpa.repositories.IPersoneRepository;
import com.betacom.jpa.repositories.ISocioRepository;
import com.betacom.jpa.services.interfaces.IPersoneServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PersoneImpl implements IPersoneServices{
	private final IPersoneRepository perR;
	
	@Transactional
	@Override
	public void create(PersoneReq req) throws Exception {
		log.debug("create {}", req);
		Personne p = new Personne();
		p.setCognome(req.getCognome());
		p.setColore(req.getColore());
		p.setEmail(req.getEmail());
		p.setNome(req.getNome());
		
		perR.save(p);
	}

	@Transactional
	@Override
	public void update(PersoneReq req) throws Exception {
		log.debug("update {}", req);
		Personne p = perR.findById(req.getId())
				.orElseThrow(() -> new AcademyException("persone_ntfnd"));
		Optional.ofNullable(req.getCognome()).ifPresent(p::setCognome);
		Optional.ofNullable(req.getNome()).ifPresent(p::setNome);
		Optional.ofNullable(req.getColore()).ifPresent(p::setColore);
		Optional.ofNullable(req.getEmail()).ifPresent(p::setEmail);
		
		perR.save(p);
		
	}
	
	@Transactional
	@Override
	public void delete(Integer id) throws Exception {
		log.debug("update {}", id);
		Personne p = perR.findById(id)
				.orElseThrow(() -> new AcademyException("persone_ntfnd"));
		
		perR.delete(p);
	}

	@Override
	public List<PersoneDTO> list() {
		log.debug("list ");
		List<Personne> lP = perR.findAll();
		return lP.stream()
				.map(p -> PersoneDTO.builder()
						.id(p.getId())
						.cognome(p.getCognome())
						.nome(p.getNome())
						.colore(p.getColore())
						.email(p.getEmail())
						.build()
						).toList();
	}

	@Override
	public PersoneDTO getById(Integer id) throws Exception {
		log.debug("getById {}", id);
		Personne p = perR.findById(id)
				.orElseThrow(() -> new AcademyException("persone_ntfnd"));
		return PersoneDTO.builder()
				.id(p.getId())
				.cognome(p.getCognome())
				.nome(p.getNome())
				.colore(p.getColore())
				.email(p.getEmail())
				.build();
	}

}
