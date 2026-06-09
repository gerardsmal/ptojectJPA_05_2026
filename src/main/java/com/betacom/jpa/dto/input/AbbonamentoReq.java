package com.betacom.jpa.dto.input;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class AbbonamentoReq {
	private Integer id;
	private String dataIscrizione;
	private Integer socioId;
	private Integer durataValidita;

}
