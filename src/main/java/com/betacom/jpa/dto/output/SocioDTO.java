package com.betacom.jpa.dto.output;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class SocioDTO {
	private Integer id;
	private String cognome;
	private String nome;
	private String codiceFiscale;	
	private String email;
	private CertificatoDTO certificato;
	private List<AbbonamentoDTO> abbonamento;

}
