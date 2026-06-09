package com.betacom.jpa.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.jpa.dto.input.CertificatoReq;
import com.betacom.jpa.dto.output.ResponseDTO;
import com.betacom.jpa.services.interfaces.ICertificatoServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/certificato")
public class CertificatoController {

	private final ICertificatoServices cerS;
	
	
	@PutMapping("update")
	public ResponseEntity<ResponseDTO> update(@RequestBody (required = true) CertificatoReq req) {
		ResponseDTO r = new ResponseDTO();
		HttpStatus status = HttpStatus.OK;
		try {
			cerS.update(req);
			r.setMsg("updated...");
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}

}
