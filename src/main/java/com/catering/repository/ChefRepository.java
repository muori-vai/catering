package com.catering.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.catering.model.Chef;

public interface ChefRepository extends CrudRepository<Chef, Long> {

	public boolean existsByNomeAndCognomeAndNazionalita(String nome, String cognome, String nazionalita);
	
	public List<Chef> findByOrderByCognomeAscNomeAscNazionalita();
}
