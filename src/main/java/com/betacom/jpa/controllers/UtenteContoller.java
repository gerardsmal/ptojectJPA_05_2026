package com.betacom.jpa.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.jpa.dto.input.UtenteReq;
import com.betacom.jpa.dto.input.ValidationGroups;
import com.betacom.jpa.dto.output.ResponseDTO;
import com.betacom.jpa.services.interfaces.IUtenteServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/utente")
public class UtenteContoller {
	private final IUtenteServices utS;
	
	@PostMapping("create")
	public ResponseEntity<ResponseDTO> create(
			@RequestBody (required = true) @Validated(ValidationGroups.Create.class) UtenteReq req) throws Exception{
		utS.create(req);
		return ResponseEntity.ok(ResponseDTO.builder()
				.msg("created...")
				.build());
	
	}
	
	@PatchMapping("update")
	public ResponseEntity<ResponseDTO> update(
			@RequestBody (required = true) @Validated(ValidationGroups.Update.class) UtenteReq req) throws Exception{
		utS.update(req);			
		return ResponseEntity.ok(ResponseDTO.builder()
					.msg("updated...")
					.build());
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<ResponseDTO> delete(@PathVariable (required = true) String id) throws Exception{
		utS.delete(id);
		return ResponseEntity.ok(ResponseDTO.builder()
					.msg("deleted...")
					.build());
	}
	
	@GetMapping("/list")
	public ResponseEntity<Object> listByAttivita() throws Exception{
			return ResponseEntity.ok(utS.list());
	}

	@GetMapping("getByUserName")
	public ResponseEntity<Object> getByUserName(@RequestParam (required = true) String id) throws Exception{
			return ResponseEntity.ok(utS.getByUserName(id));
	}

}
