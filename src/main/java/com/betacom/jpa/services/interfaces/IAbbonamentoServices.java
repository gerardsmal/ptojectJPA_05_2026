package com.betacom.jpa.services.interfaces;

import com.betacom.jpa.dto.input.AbbonamentoReq;
import com.betacom.jpa.dto.output.AbbonamentoDTO;
import com.betacom.jpa.dto.output.RicevutaDTO;

public interface IAbbonamentoServices {

	void create(AbbonamentoReq req) throws Exception;
	void update(AbbonamentoReq req) throws Exception;
	void delete(Integer id, Integer socioID) throws Exception;
	
	void createAbbonamentoAttivita(AbbonamentoReq req) throws Exception;
	void deleteAbbonamentoAttivita(Integer id, Integer sttivitaId) throws Exception;
	
	
	RicevutaDTO buildRicevuta(Integer id) throws Exception;
	
	AbbonamentoDTO getAbbonamentoById(Integer id) throws Exception;
}
