package com.catering.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catering.model.Chef;
import com.catering.repository.ChefRepository;

@Service
public class ChefService {

	@Autowired
	private ChefRepository chefRepository;

	@Transactional
	public void save(Chef chef) {
		chefRepository.save(chef);
	}
	
	@Transactional
	public void delete(Chef chef) {
		chefRepository.delete(chef);
	}
	
	@Transactional
	public void deleteById(Long id) {
		chefRepository.deleteById(id);
	}

	public Chef findById(Long id) {
		return chefRepository.findById(id).get();
	}
	
	public List<Chef> findAll() {
		List<Chef> chefs = new ArrayList<Chef>();
		
		//dobbiamo fare così perché rep.findAll ritorna un iteratore
		for(Chef c: chefRepository.findAll()) {
			chefs.add(c);
		}
		
		return chefs;
	}
	
	public boolean alreadyExists(Chef chef) {
		return chefRepository.existsByNomeAndCognomeAndNazionalita(chef.getNome(), chef.getCognome(), chef.getNazionalita());
	}
}
