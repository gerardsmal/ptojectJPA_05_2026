package com.betacom.jpa.services.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.betacom.jpa.dto.input.UtenteReq;
import com.betacom.jpa.dto.output.UtenteDTO;
import com.betacom.jpa.enums.Roles;
import com.betacom.jpa.exceptions.AcademyException;
import com.betacom.jpa.models.Utente;
import com.betacom.jpa.repositories.IUtenteRepository;
import com.betacom.jpa.services.interfaces.IUtenteServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@Service
public class UtenteImpl implements IUtenteServices{
	
	private final IUtenteRepository utR;
	
	@Transactional
	@Override
	public void create(UtenteReq req) throws Exception {
		log.debug("create {}", req);
		if (utR.existsById(req.getUserName()))
			throw new AcademyException("user_exist");
		Utente ut = new Utente();
		ut.setUserName(req.getUserName());
		ut.setEmail(req.getEmail());
		ut.setPwd(req.getPwd());
		ut.setRole(Roles.valueOf(req.getRole()));
		utR.save(ut);
		
	}
	@Transactional
	@Override
	public void update(UtenteReq req) throws Exception {
		log.debug("update {}", req);
		Utente ut = utR.findById(req.getUserName())
				.orElseThrow(() -> new AcademyException("user_ntfnd"));
		
		Optional.ofNullable(req.getEmail()).ifPresent(ut::setEmail);
		Optional.ofNullable(req.getPwd()).ifPresent(ut::setPwd);
		Optional.ofNullable(req.getRole()).ifPresent(r ->ut.setRole(Roles.valueOf(r)));
		utR.save(ut);
		
	}

	@Transactional
	@Override
	public void delete(String userName) throws Exception {
		log.debug("delete {}", userName);
		Utente ut = utR.findById(userName)
				.orElseThrow(() -> new AcademyException("user_ntfnd"));
		utR.delete(ut);
	}

	@Override
	public List<UtenteDTO> list() {
		log.debug("list");
		List<Utente> lU = utR.findAll();		
		return lU.stream()
			.map(u -> UtenteDTO.builder()
					.userName(u.getUserName())
					.pwd(u.getPwd())
					.role(u.getRole().toString())
					.email(u.getEmail())
					.build()
					).toList();	
				
	}

	@Override
	public UtenteDTO getByUserName(String userName) throws Exception {
		log.debug("getByUserName {}", userName);
		Utente u = utR.findById(userName)
				.orElseThrow(() -> new AcademyException("user_ntfnd"));
		
		return UtenteDTO.builder()
				.userName(u.getUserName())
				.pwd(u.getPwd())
				.role(u.getRole().toString())
				.email(u.getEmail())
				.build();
	}

}
