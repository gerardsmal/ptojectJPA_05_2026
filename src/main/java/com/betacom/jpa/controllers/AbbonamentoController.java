package com.betacom.jpa.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.jpa.dto.input.AbbonamentoReq;
import com.betacom.jpa.dto.output.ResponseDTO;
import com.betacom.jpa.services.interfaces.IAbbonamentoServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/abbonamento")
public class AbbonamentoController {
	
	private final IAbbonamentoServices abboS;
	
	
	@PostMapping("create")
	public ResponseEntity<ResponseDTO> create(@RequestBody (required = true) AbbonamentoReq req) {
		ResponseDTO r = new ResponseDTO();
		HttpStatus status = HttpStatus.OK;
		try {
			abboS.create(req);
			r.setMsg("created...");
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}
	

	@PutMapping("update")
	public ResponseEntity<ResponseDTO> update(@RequestBody (required = true) AbbonamentoReq req) {
		ResponseDTO r = new ResponseDTO();
		HttpStatus status = HttpStatus.OK;
		try {
			abboS.update(req);
			r.setMsg("updated...");
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}

	@DeleteMapping("delete/{id}/{socioId}")
	public ResponseEntity<ResponseDTO> delete(
			@PathVariable (required = true) Integer id,
			@PathVariable (required = true) Integer socioId
			) {
		ResponseDTO r = new ResponseDTO();
		HttpStatus status = HttpStatus.OK;
		try {
			abboS.delete(id,socioId);
			r.setMsg("deleted...");
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}

}
