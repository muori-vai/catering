package com.catering.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.catering.model.Ingrediente;

public interface IngredienteRepository extends CrudRepository<Ingrediente, Long> {

	//public boolean existsByNomeAndDescrizioneAndOrigineAndPiatto(String nome, String descrizione, String origine, Piatto piatto);
	
	public boolean existsByNomeAndDescrizioneAndOrigine(String nome, String descrizione, String origine);
	
	public List<Ingrediente> findByOrderByNomeAscOrigineAsc();
}
