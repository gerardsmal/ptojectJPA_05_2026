package com.betacom.jpa.dto.input;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class AbbonamentoReq {
	@NotNull (groups = {ValidationGroups.Update.class , ValidationGroups.Attivita.class } , message ="abb.no.id.update")
	private Integer id;
	
	@NotNull (groups = ValidationGroups.Create.class , message ="abb.no.date")
	private String dataIscrizione;
	
	private Integer durataValidita;
	
	@NotNull (groups = {ValidationGroups.Create.class, ValidationGroups.Update.class }, message ="abb.no.id")	
	private Integer socioId;
	
	@NotNull (groups = ValidationGroups.Attivita.class , message ="attiv.ntfnd")
	private Integer attivitaId;
}
