package com.betacom.jpa.dto.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
public class RicevutaDTO {

	private String nome;
	private String cognome;
	private Long   totale;
	List<AttivitaDTO> riga;
}
