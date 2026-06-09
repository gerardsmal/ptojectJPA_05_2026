package com.betacom.jpa.dto.output;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@Builder
@ToString
public class CertificatoDTO {
	private Integer id;
	private String tipo;    // false normale true agonisctico
	private LocalDate dataCertificato;

}
