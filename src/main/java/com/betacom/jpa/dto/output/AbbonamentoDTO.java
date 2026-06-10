package com.betacom.jpa.dto.output;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AbbonamentoDTO {
	private Integer id;
	private LocalDate dataIscrizione;
	private Integer durataValidita;
	private List<AttivitaDTO> attivita;

}
