package com.catering.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catering.model.Ingrediente;
import com.catering.model.Piatto;
import com.catering.repository.IngredienteRepository;
import com.catering.repository.PiattoRepository;

@Service
public class IngredienteService {

	@Autowired
	private IngredienteRepository ingredienteRepository;
	
	@Autowired
	private PiattoRepository piattoRepository;

	@Transactional
	public void save(Ingrediente ingrediente) {
		ingredienteRepository.save(ingrediente);
	}
	
	@Transactional
	public void delete(Ingrediente ingrediente) {
		ingredienteRepository.delete(ingrediente);
	}
	
	@Transactional
	public void deleteById(Long id) {
		ingredienteRepository.deleteById(id);
	}

	public Ingrediente findById(Long id) {
		return ingredienteRepository.findById(id).get();
	}
	
	public List<Ingrediente> findAll() {
		List<Ingrediente> ingredienti = new ArrayList<Ingrediente>();
		
		//dobbiamo fare così perché findAll ritorna un iteratore
//		for(Ingrediente i: ingredienteRepository.findAll()) {
//			ingredienti.add(i);
//		}
		//per avere la lista degli ingredienti in ordine alfabetico
		for(Ingrediente i: ingredienteRepository.findByOrderByNomeAscOrigineAsc()) {
			ingredienti.add(i);
		}
		
		return ingredienti;
	}
	
	public List<Ingrediente> findAllByPiatto(Long id) {
		List<Ingrediente> ingredienti = new ArrayList<Ingrediente>();
		
		Piatto p = piattoRepository.findById(id).get();
		
		ingredienti = p.getIngredienti();
		
		return ingredienti;
	}
		
//	public List<Ingrediente> findAllByPiattoAlt(Long id) {
//		List<Ingrediente> ingredienti = new ArrayList<Ingrediente>();
//		
//		//dobbiamo fare così perché findAll ritorna un iteratore
//		for(Ingrediente i: ingredienteRepository.findAll()) {
//			//if(i.getPiatto().getId().equals(id))
//			//	ingredienti.add(i);
//			for(Piatto p: i.getPiatti()) {
//				if(p.getId().equals(id))
//					ingredienti.add(i);
//			}
//		}
//		
//		return ingredienti;
//	}
	
	public boolean alreadyExists(Ingrediente ingrediente) {
		//return ingredienteRepository.existsByNomeAndDescrizioneAndOrigineAndPiatto
		//(ingrediente.getNome(), ingrediente.getDescrizione(), ingrediente.getOrigine(), ingrediente.getPiatto());
		return ingredienteRepository.existsByNomeAndDescrizioneAndOrigine(ingrediente.getNome(), ingrediente.getDescrizione(), ingrediente.getOrigine());
	}
}
