package com.betacom.jpa.controllers;

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
	public ResponseEntity<ResponseDTO> create(@RequestBody (required = true) AbbonamentoReq req) throws Exception{
			abboS.create(req);
			return ResponseEntity.ok(ResponseDTO.builder()
					.msg("created...")
					.build());
	}
	

	@PutMapping("update")
	public ResponseEntity<ResponseDTO> update(@RequestBody (required = true) AbbonamentoReq req) throws Exception {
			abboS.update(req);
			return ResponseEntity.ok(ResponseDTO.builder()
					.msg("updated...")
					.build());
	}

	@DeleteMapping("delete/{id}/{socioId}")
	public ResponseEntity<ResponseDTO> delete(
			@PathVariable (required = true) Integer id,
			@PathVariable (required = true) Integer socioId
			) throws Exception{
			abboS.delete(id,socioId);
			return ResponseEntity.ok(ResponseDTO.builder()
					.msg("deleted...")
					.build());
	}

	@PostMapping("addAttivita")
	public ResponseEntity<ResponseDTO> addAttivita(@RequestBody (required = true) AbbonamentoReq req) throws Exception{
			abboS.createAbbonamentoAttivita(req);
			return ResponseEntity.ok(ResponseDTO.builder()
					.msg("added...")
					.build());
	}

	@DeleteMapping("deleteAttivita/{id}/{attivitaID}")
	public ResponseEntity<ResponseDTO> deleteAttivita(
			@PathVariable (required = true) Integer id,
			@PathVariable (required = true) Integer attivitaID) throws Exception{
			abboS.deleteAbbonamentoAttivita(id, attivitaID);
			return ResponseEntity.ok(ResponseDTO.builder()
					.msg("deleted...")
					.build());
	}

	
	@GetMapping("getAbbonamentoById")
	public ResponseEntity<Object> getAbbonamentoById(@RequestParam (required = true) Integer id) throws Exception{
		return ResponseEntity.ok(abboS.getAbbonamentoById(id)) ;
	}

	@GetMapping("buildRicevuta")
	public ResponseEntity<Object> buildRicevuta(@RequestParam (required = true) Integer id) throws Exception{
			return ResponseEntity.ok(abboS.buildRicevuta(id));
	}

}
