package com.betacom.jpa.models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table ( name = "abbonamento_socio")
public class Abbonamento {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column (
			name="data_iscrizione",
			nullable = false
			)
	private LocalDate dataIscrizione;
	
	@Column (
			name="durata_validita",
			nullable = false
			)
	private Integer durataValidita;
	
	@ManyToOne
	@JoinColumn (
			name="id_socio",
			foreignKey = @ForeignKey(name ="fk_abbonamento_socio" )	
			)
	private Socio socio;
	
	@ManyToMany (fetch = FetchType.EAGER)
	@JoinTable(
			name="abbonamento_attivita",
			joinColumns = @JoinColumn (name = "abbonamento_id" ),
			inverseJoinColumns = @JoinColumn (name = "attivita_id" )
			)
	List<Attivita> attivita;
	
	@OneToMany(
			mappedBy = "abbonamento",
			fetch = FetchType.EAGER
			)
	private List<PagamentoRicevute> pagamentoRicevute;

}
