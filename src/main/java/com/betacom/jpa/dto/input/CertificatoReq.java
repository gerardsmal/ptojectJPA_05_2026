package com.betacom.jpa.dto.input;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
public class CertificatoReq {
	private Integer id;
	private Boolean tipo;    // false normale true agonisctico
	private String dataCertificato;
	private Integer socioId;

}
