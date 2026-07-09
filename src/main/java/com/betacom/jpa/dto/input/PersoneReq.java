package com.betacom.jpa.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PersoneReq {
	@NotNull (groups = ValidationGroups.Update.class , message ="persone.no.id")
	private Integer id;
	@NotNull (groups = ValidationGroups.Create.class , message ="cognome_empty")
	private String cognome;
	@NotNull (groups = ValidationGroups.Create.class , message ="nome_empty")
	private String nome;
	@NotNull (groups = ValidationGroups.Create.class , message ="email_empty")
	private String email;	

	private String colore;
}
