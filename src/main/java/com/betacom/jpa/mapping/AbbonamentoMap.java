package com.betacom.jpa.mapping;

import java.util.List;

import com.betacom.jpa.dto.output.AbbonamentoDTO;
import com.betacom.jpa.models.Abbonamento;

public class AbbonamentoMap {
	
	public static List<AbbonamentoDTO> buildAbbonamentoDTOList(List<Abbonamento> lA){
		return lA.stream()
				.map (a -> buildAbbonamentoDTO(a)
						).toList();
		
	}
	public static AbbonamentoDTO buildAbbonamentoDTO(Abbonamento a) {
		return AbbonamentoDTO.builder()
				.id(a.getId())
				.dataIscrizione(a.getDataIscrizione())
				.durataValidita(a.getDurataValidita())
				.attivita(AttivitaMap.buildAttivitaDTOList(a.getAttivita()))
				.build();
	}
}
