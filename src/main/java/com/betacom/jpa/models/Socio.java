package com.betacom.jpa.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table (
		name="socio",
		uniqueConstraints = {
			@UniqueConstraint(
				name="uk_codice_fiscale",
				columnNames = "codice_fiscale"		
			)
		}
	)
public class Socio {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column (
			length = 100,
			nullable = false
			)
	private String cognome;

	@Column (
			length = 100,
			nullable = false
			)
	private String nome;
	
	@Column(
			name="codice_fiscale",
			nullable = false,
			length = 16,
			unique = true
			)
	private String codiceFiscale;
	
	private String email;
	
	@OneToOne(
			cascade = CascadeType.REMOVE,
			orphanRemoval = true			
			)
	@JoinColumn(
			name="certificato_id",
			referencedColumnName = "id",
			foreignKey = @ForeignKey(name ="fk_socio_certificato" )
			)
	private Certificato certificato;
	
	@OneToMany(
			mappedBy = "socio",
			fetch = FetchType.EAGER
			)
	private List<Abbonamento> abbonamento;

}
