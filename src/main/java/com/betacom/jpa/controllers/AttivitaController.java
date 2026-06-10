package com.betacom.jpa.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.jpa.dto.input.AbbonamentoReq;
import com.betacom.jpa.dto.input.AttivitaReq;
import com.betacom.jpa.dto.output.ResponseDTO;
import com.betacom.jpa.services.interfaces.IAbbonamentoServices;
import com.betacom.jpa.services.interfaces.IAttivitaServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/attivita")
public class AttivitaController {

	private final IAttivitaServices attivS;
	
	
	@PostMapping("create")
	public ResponseEntity<ResponseDTO> create(@RequestBody (required = true) AttivitaReq req) {
		ResponseDTO r = new ResponseDTO();
		HttpStatus status = HttpStatus.OK;
		try {
			attivS.create(req);
			r.setMsg("created...");
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}
	

	@PutMapping("update")
	public ResponseEntity<ResponseDTO> update(@RequestBody (required = true) AttivitaReq req) {
		ResponseDTO r = new ResponseDTO();
		HttpStatus status = HttpStatus.OK;
		try {
			attivS.update(req);
			r.setMsg("updated...");
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<ResponseDTO> delete(@PathVariable (required = true) Integer id) {
		ResponseDTO r = new ResponseDTO();
		HttpStatus status = HttpStatus.OK;
		try {
			attivS.delete(id);
			r.setMsg("deleted...");
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}
	
	@GetMapping("/list")
	public ResponseEntity<Object> list(){
		Object r = new Object();
		HttpStatus status = HttpStatus.OK;
		try {
			r = attivS.list();
		} catch (Exception e) {
			r=e.getMessage();
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}
	
	@GetMapping("getById")
	public ResponseEntity<Object> getById(@RequestParam (required = true) Integer id){
		Object r = new Object();
		HttpStatus status = HttpStatus.OK;
		try {
			r = attivS.getById(id);
		} catch (Exception e) {
			r=e.getMessage();
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}


}
