package com.betacom.jpa.mapping;

import java.util.List;

import com.betacom.jpa.dto.output.AbbonamentoDTO;
import com.betacom.jpa.models.Abbonamento;

public class AbbonamentoMap {
	
	public static List<AbbonamentoDTO> buildAbbonamentoDTOList(List<Abbonamento> lA){
		return lA.stream()
				.map (a -> AbbonamentoDTO.builder()
						.id(a.getId())
						.dataIscrizione(a.getDataIscrizione())
						.durataValidita(a.getDurataValidita())
						.build()
						).toList();
		
	}
}
