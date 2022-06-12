package com.catering.repository;

import org.springframework.data.repository.CrudRepository;

import com.catering.model.Buffet;
import com.catering.model.Chef;

public interface BuffetRepository extends CrudRepository<Buffet, Long> {

	public boolean existsByNomeAndDescrizioneAndChef(String nome, String descrizione, Chef Chef);
}
