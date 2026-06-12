package com.betacom.jpa.models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table ( name = "pagamento_ricevute")
public class PagamentoRicevute {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(
			name="prezzo_pagatp",
			nullable = false			
			)	
	private Long prezzoPagato;

	
	@Column (
			name="data_pagamento",
			nullable = false
			)
	private LocalDate dataPagamento;

	@ManyToOne
	@JoinColumn (
			name="id_attivita",
			foreignKey = @ForeignKey(name ="fk_pagamento_attivita" )	
			)
	private Attivita attivita;

	@ManyToOne
	@JoinColumn (
			name="id_abbonamento",
			foreignKey = @ForeignKey(name ="fk_pagamento_abbonamento" )	
			)
	private Abbonamento abbonamento;

}
