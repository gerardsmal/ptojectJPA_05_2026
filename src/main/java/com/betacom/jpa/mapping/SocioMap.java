package com.betacom.jpa.mapping;

import java.util.List;

import com.betacom.jpa.dto.output.CertificatoDTO;
import com.betacom.jpa.dto.output.SocioDTO;
import com.betacom.jpa.models.Socio;

public class SocioMap {
	public static List<SocioDTO> buildSocioDTOList(List<Socio> lS){
		
		return lS.stream()
				.map(s -> buildSocioDTO(s)
						).toList();						
		
	}
	
	public static SocioDTO buildSocioDTO(Socio s) {
		return SocioDTO.builder()
				.id(s.getId())
				.cognome(s.getCognome())
				.nome(s.getNome())
				.codiceFiscale(s.getCodiceFiscale())
				.email(s.getEmail())
				.certificato(s.getCertificato() == null ? null : CertificatoDTO.builder()
						.id(s.getCertificato().getId())
						.tipo(s.getCertificato().getTipo() ? "agonistico" : "normale")
						.dataCertificato(s.getCertificato().getDataCertificato())
						.build()
						)
				.abbonamento(AbbonamentoMap.buildAbbonamentoDTOList(s.getAbbonamento()))
				.build();
	}
}
