package com.betacom.jpa.services.interfaces;

import java.util.List;

import com.betacom.jpa.dto.input.SocioReq;
import com.betacom.jpa.dto.output.SocioDTO;

public interface ISocioServices {
	void create (SocioReq req) throws Exception;
	void    update (SocioReq req) throws Exception;
	void delete(Integer id)  throws Exception;
	
	List<SocioDTO> list() throws Exception;
	List<SocioDTO> listByAttivita(Integer id) throws Exception;
	
	
	SocioDTO getById(Integer id) throws Exception;

}
