package com.catering.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.catering.controller.validator.PiattoValidator;
import com.catering.model.Piatto;
import com.catering.service.BuffetService;
import com.catering.service.IngredienteService;
import com.catering.service.PiattoService;

@Controller
public class PiattoController {

	@Autowired
	private PiattoService piattoService;

	@Autowired
	private IngredienteService ingredienteService;

	@Autowired
	private BuffetService buffetService;

	@Autowired
	private PiattoValidator piattoValidator;

	@Transactional
	@PostMapping("/buffet/{id}/piatto") // String perché ritorna il nome della vista
	public String addPiatto(@Valid @ModelAttribute("piatto") Piatto piatto, BindingResult bindingResult,
			@PathVariable("id") Long id, Model model) {

		piatto.setBuffet(buffetService.findById(id));
		model.addAttribute("listaIngredienti", ingredienteService.findAll());
		
		piattoValidator.validate(piatto, bindingResult);

		if (!bindingResult.hasErrors()) {
			piattoService.save(piatto);
			model.addAttribute("piatto", piatto);

			return "piattoInserito.html";
		}
		
		return "piattoForm.html";
	}

	@GetMapping("/buffet/{id}/piatti")
	public String getPiatti(@PathVariable("id") Long id, Model model) {
		List<Piatto> piatti = piattoService.findAllByBuffet(id);
		model.addAttribute("piatti", piatti);

		return "piatti.html";
	}

	@GetMapping("/piatti")
	public String getPiatti(Model model) {
		List<Piatto> piatti = piattoService.findAll();
		model.addAttribute("piatti", piatti);

		return "piatti.html";
	}

	// tra parentesi graffe perché l'id è un parametro
	// @PathVariable("id") indica che il parametro Long id proviene dal parametro in
	// /piatto/{id}
	@GetMapping("/piatto/{id}")
	public String getPiatto(@PathVariable("id") Long id, Model model) {
		Piatto piatto = piattoService.findById(id);
		model.addAttribute("piatto", piatto);
		// List<Ingrediente> ingredienti = piatto.getIngredienti();
		// model.addAttribute("ingredienti", ingredienti);

		return "piatto.html";
	}

	@Transactional
	@GetMapping("/buffet/{id}/piattoForm")
	public String getPiattoForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("piatto", new Piatto());
		model.addAttribute("listaIngredienti", ingredienteService.findAll());

		return "piattoForm.html";
	}

//	@GetMapping("/piatto/{id}/ingredienti")
//	public String getIngredientiByPiatto(@PathVariable("id") Long id, Model model) {
//		List<Ingrediente> ingredienti = piattoService.findById(id).getIngredienti();
//		model.addAttribute("ingredienti", ingredienti);
//		
//		return "ingredienti.html";
//	}

	@GetMapping("/deletePiatto/{id}")
	public String deletePiatto(@PathVariable("id") Long id, Model model) {
		model.addAttribute("piatto", this.piattoService.findById(id));

		return "deletePiatto.html";
	}

	@GetMapping("/confirmDeletePiatto/{id}")
	public String confirmDeletePiatto(@PathVariable("id") Long id,
			/* @RequestParam(value="action", required=true) String action, */ Model model) {
		/*
		 * if(action.equals("Elimina")) { this.piattoService.deleteById(id); }
		 */

		this.piattoService.deleteById(id);

		model.addAttribute("piatti", piattoService.findAll());

		return "piatti.html";
	}

	@GetMapping("/editPiattoForm/{id}")
	public String editPiattoForm(@PathVariable Long id, Model model) {
		model.addAttribute("piatto", this.piattoService.findById(id));
		model.addAttribute("idBuffet", this.piattoService.findById(id).getBuffet().getId());
		model.addAttribute("listaIngredienti", this.ingredienteService.findAll());

		return "editPiattoForm.html";
	}

	@Transactional
	@PostMapping("/editPiatto/{id}")
	public String editPiatto(@Valid @ModelAttribute("piatto") Piatto piatto, BindingResult bindingResult,
			@PathVariable Long id, Model model) {

		Piatto vecchioPiatto = this.piattoService.findById(id);

		//model.addAttribute("idBuffet", vecchioPiatto.getBuffet().getId());
		model.addAttribute("listaIngredienti", ingredienteService.findAll());

		if (!vecchioPiatto.equals(piatto))
			this.piattoValidator.validate(piatto, bindingResult);

		if (!bindingResult.hasErrors()) {
			vecchioPiatto.setNome(piatto.getNome());
			vecchioPiatto.setDescrizione(piatto.getDescrizione());
			vecchioPiatto.setIngredienti(piatto.getIngredienti());
			
			this.piattoService.save(vecchioPiatto);
			model.addAttribute("piatto", vecchioPiatto);
			
			return "piattoModificato.html";
		}

		return "editPiattoForm.html";
	}
}