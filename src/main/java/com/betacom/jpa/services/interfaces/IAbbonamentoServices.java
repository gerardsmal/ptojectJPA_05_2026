package com.betacom.jpa.services.interfaces;

import com.betacom.jpa.dto.input.AbbonamentoReq;

public interface IAbbonamentoServices {

	void create(AbbonamentoReq req) throws Exception;
	void update(AbbonamentoReq req) throws Exception;
	void delete(Integer id, Integer socioID) throws Exception;
}
