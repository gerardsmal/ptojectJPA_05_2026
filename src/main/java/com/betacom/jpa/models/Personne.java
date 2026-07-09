package com.betacom.jpa.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table (name="persone_angular")
public class Personne {
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

	@Column (
			length = 100,
			nullable = false
			)
	private String email;
	
	private String colore;
}
