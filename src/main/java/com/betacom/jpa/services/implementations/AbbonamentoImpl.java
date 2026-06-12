package com.betacom.jpa.services.implementations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.jpa.dto.input.AbbonamentoReq;
import com.betacom.jpa.dto.output.AbbonamentoDTO;
import com.betacom.jpa.dto.output.AttivitaDTO;
import com.betacom.jpa.dto.output.RicevutaDTO;
import com.betacom.jpa.mapping.AbbonamentoMap;
import com.betacom.jpa.mapping.AttivitaMap;
import com.betacom.jpa.models.Abbonamento;
import com.betacom.jpa.models.Attivita;
import com.betacom.jpa.models.PagamentoRicevute;
import com.betacom.jpa.models.Socio;
import com.betacom.jpa.repositories.IAbbonamentoRepository;
import com.betacom.jpa.repositories.IAttivitaRepository;
import com.betacom.jpa.repositories.IPagementoRicevuteRepository;
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
	private final IPagementoRicevuteRepository parR;
	
	@Transactional
	@Override
	public void create(AbbonamentoReq req) throws Exception {
		log.debug("create :{}", req);
		if (req.getSocioId() == null)
			throw new AcademyException("abb.no.id");
		Socio soc = repS.findById(req.getSocioId())
				.orElseThrow(() -> new AcademyException("socio.ntfnd"));
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
			throw new AcademyException("abb.no.id");
		Socio soc = repS.findById(req.getSocioId())
				.orElseThrow(() -> new AcademyException("socio.ntfnd"));
		
		Abbonamento abb = soc.getAbbonamento().stream()
		        .filter(a -> a.getId().equals(req.getId()))
		        .findFirst()
		        .orElseThrow(() -> new AcademyException("abb.ntfnd"));
		
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
				.orElseThrow(() -> new AcademyException("socio.ntfnd"));

		Abbonamento abb = soc.getAbbonamento().stream()
		        .filter(a -> a.getId().equals(id))
		        .findFirst()
		        .orElseThrow(() -> new AcademyException("abb.ntfnd"));

		repA.delete(abb);
		
	}

	@Transactional
	@Override
	public void createAbbonamentoAttivita(AbbonamentoReq req) throws Exception {
		log.debug("createAbbonamentoAttivita {}", req);
		
		Abbonamento abb = repA.findById(req.getId())
				.orElseThrow(() -> new AcademyException("abb.ntfnd"));
		
		Attivita attiv = repAttiv.findById(req.getAttivitaId())
				.orElseThrow(() -> new AcademyException("attiv.ntfnd"));
		
		if (abb.getAttivita().contains(attiv))
			throw new AcademyException("abb.attiv.fnd");
		abb.getAttivita().add(attiv);
		repA.save(abb);
		
	}

	
	@Override
	public AbbonamentoDTO getAbbonamentoById(Integer id) throws Exception {
		log.debug("getAbbonamentoBtId {}", id);
		Abbonamento abb = repA.findById(id)
				.orElseThrow(() -> new AcademyException("abb.ntfnd"));

		return AbbonamentoMap.buildAbbonamentoDTO(abb);
	}

	@Transactional
	@Override
	public void deleteAbbonamentoAttivita(Integer id, Integer attivitaId) throws Exception {
		log.debug("deleteAbbonamentoAttivita id abbonamento {} idAttivita {}", id, attivitaId);
		Abbonamento abb = repA.findById(id)
				.orElseThrow(() -> new AcademyException("abb.ntfnd"));
		
		abb.getAttivita().stream()
			.filter(at -> at.getId().equals(attivitaId))
			.findFirst()
			.ifPresent(abb.getAttivita() :: remove);

		repA.save(abb);		
	}
	
	@Transactional
	@Override
	public RicevutaDTO buildRicevuta(Integer id) throws Exception {
		log.debug("buildRicevuta : {}", id);
		Abbonamento abb = repA.findById(id)
				.orElseThrow(() -> new AcademyException("abb.ntfnd"));
		if (abb.getAttivita().isEmpty())
			throw new AcademyException("abb.no.attiv");
		
		RicevutaDTO ric = RicevutaDTO.builder()
				.cognome(abb.getSocio().getCognome())
				.nome(abb.getSocio().getNome())
				.riga(new ArrayList<AttivitaDTO>())
				.build();
		
		List<Attivita> daPagare = abb.getAttivita().stream()
			    .filter(at -> abb.getPagamentoRicevute().stream()
			            .noneMatch(p ->  p.getAttivita().equals(at))
			    ).toList();
		
		if (daPagare.size() == 0)
			throw new AcademyException("abb.already.pay");
		
		daPagare.forEach(pa -> {
			ric.getRiga().add(AttivitaMap.buildAttivitaDTO(pa));
			parR.save(PagamentoRicevute.builder()
					.dataPagamento(LocalDate.now())
					.attivita(pa)
					.abbonamento(abb)
					.prezzoPagato(pa.getPrezzo())
					.build()
					);			
		});
		
		ric.setTotale(daPagare.stream()
			        .mapToLong(Attivita::getPrezzo)  // genera una map di primitiva long
			        .sum());	                     // somma tutti gli elementi della stram primitiva
		
		return ric;
	}
}