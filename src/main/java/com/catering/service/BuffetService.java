package com.catering.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catering.model.Buffet;
import com.catering.repository.BuffetRepository;

@Service
public class BuffetService {

	@Autowired
	private BuffetRepository buffetRepository;

	@Transactional
	public void save(Buffet buffet) {
		buffetRepository.save(buffet);
	}
	
	@Transactional
	public void delete(Buffet buffet) {
		buffetRepository.delete(buffet);
	}
	
	@Transactional
	public void deleteById(Long id) {
		buffetRepository.deleteById(id);
	}

	public Buffet findById(Long id) {
		return buffetRepository.findById(id).get();
	}
	
	public List<Buffet> findAll() {
		List<Buffet> buffets = new ArrayList<Buffet>();
		
		//dobbiamo fare così perché findAll ritorna un iteratore
		for(Buffet c: buffetRepository.findAll()) {
			buffets.add(c);
		}
		
		return buffets;
	}
	
	public List<Buffet> findAllByChef(Long id) {
		List<Buffet> buffets = new ArrayList<Buffet>();
		
		//dobbiamo fare così perché findAll ritorna un iteratore
		for(Buffet b: buffetRepository.findAll()) {
			if(b.getChef().getId().equals(id))
				buffets.add(b);
		}
		
		return buffets;
	}
	
	public boolean alreadyExists(Buffet buffet) {
		return buffetRepository.existsByNomeAndDescrizioneAndChef(buffet.getNome(), buffet.getDescrizione(), buffet.getChef());
	}
}
