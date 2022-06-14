package com.catering.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catering.model.Piatto;
import com.catering.repository.PiattoRepository;

@Service
public class PiattoService {
	
	@Autowired
	private PiattoRepository piattoRepository;

	@Transactional
	public void save(Piatto piatto) {
		piattoRepository.save(piatto);
	}
	
	@Transactional
	public void delete(Piatto piatto) {
		piattoRepository.delete(piatto);
	}
	
	@Transactional
	public void deleteById(Long id) {
		piattoRepository.deleteById(id);
	}

	public Piatto findById(Long id) {
		return piattoRepository.findById(id).get();
	}
	
	public List<Piatto> findAll() {
		List<Piatto> piatti = new ArrayList<Piatto>();
		
		//dobbiamo fare così perché findAll ritorna un iteratore
		for(Piatto c: piattoRepository.findByOrderByNome()) {
			piatti.add(c);
		}
		
		return piatti;
	}
	
	public List<Piatto> findAllByBuffet(Long id) {
		List<Piatto> piatti = new ArrayList<Piatto>();
		
		//dobbiamo fare così perché findAll ritorna un iteratore
		for(Piatto p: piattoRepository.findByOrderByNome()) {
			if(p.getBuffet().getId().equals(id))
				piatti.add(p);
		}
		
		return piatti;
	}
	
	public boolean alreadyExists(Piatto piatto) {
		return piattoRepository.existsByNomeAndDescrizioneAndBuffet(piatto.getNome(), piatto.getDescrizione(), piatto.getBuffet());
	}
}
