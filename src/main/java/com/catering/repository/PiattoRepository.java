package com.catering.repository;

import org.springframework.data.repository.CrudRepository;

import com.catering.model.Buffet;
import com.catering.model.Piatto;

public interface PiattoRepository extends CrudRepository<Piatto, Long> {

	public boolean existsByNomeAndDescrizioneAndBuffet(String nome, String descrizione, Buffet buffet);
}
