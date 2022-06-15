package com.catering.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
public class Piatto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String nome;
	@NotBlank
	private String descrizione;
	
	//@OneToMany(mappedBy = "piatto", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
	//private List<Ingrediente> ingredienti;
	
	@NotEmpty
	@ManyToMany(cascade = {CascadeType.PERSIST})
	private List<Ingrediente> ingredienti;
	
	@ManyToOne
	private Buffet buffet;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public List<Ingrediente> getIngredienti() {
		return ingredienti;
	}

	public void setIngredienti(List<Ingrediente> ingredienti) {
		this.ingredienti = ingredienti;
	}

	public Buffet getBuffet() {
		return buffet;
	}

	public void setBuffet(Buffet buffet) {
		this.buffet = buffet;
	}

	@Override
	public int hashCode() {
		return Objects.hash(buffet, descrizione, id, ingredienti, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Piatto other = (Piatto) obj;
		return Objects.equals(buffet, other.buffet) && Objects.equals(descrizione, other.descrizione)
				&& Objects.equals(nome, other.nome);
	}

}
