package com.betacom.jpa.dto.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SocioReq {
	@NotNull (groups = ValidationGroups.Update.class , message ="socio.no.id")
	private Integer id;
	
	@NotNull (groups = ValidationGroups.Create.class , message ="socio.no.cognome")
	@NotBlank (groups = ValidationGroups.Create.class , message ="socio.no.cognome")
	private String cognome;
	
	@NotNull (groups = ValidationGroups.Create.class , message ="socio.no.nome")
	@NotBlank (groups = ValidationGroups.Create.class , message ="socio.no.nome")
	private String nome;
	
	@NotNull (groups = ValidationGroups.Create.class , message ="socio.cfisc.invalid")
	@NotBlank (groups = ValidationGroups.Create.class , message ="socio.cfisc.invalid")
	private String codiceFiscale;
	
	@Email (groups = {ValidationGroups.Create.class, ValidationGroups.Update.class}, message ="socio.email.invalid")
	private String email;

}
