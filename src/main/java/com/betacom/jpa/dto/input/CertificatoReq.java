package com.betacom.jpa.dto.input;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CertificatoReq {
	private Boolean tipo;    // false normale true agonisctico
	private LocalDate dataCertificato;
	private Integer socioId;

}
