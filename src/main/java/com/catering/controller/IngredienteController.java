package com.catering.controller;

import java.util.ArrayList;
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

import com.catering.controller.validator.IngredienteValidator;
import com.catering.model.Ingrediente;
import com.catering.model.Piatto;
import com.catering.service.IngredienteService;
import com.catering.service.PiattoService;

@Controller
public class IngredienteController {

	@Autowired
	private IngredienteService ingredienteService;

	@Autowired
	private PiattoService piattoService;

	@Autowired
	private IngredienteValidator ingredienteValidator;

	/*
	 * Vecchia funzione
	 * 
	 * @PostMapping("/piatto/{id}/ingrediente") //String perché ritorna il nome
	 * della vista public String
	 * addIngrediente(@Valid @ModelAttribute("ingrediente") Ingrediente
	 * ingrediente, @PathVariable("id") Long id, BindingResult bindingResult, Model
	 * model) {
	 * 
	 * ingrediente.getPiatti().add(piattoService.findById(id));
	 * //ingrediente.setPiatto(piattoService.findById(id));
	 * ingredienteValidator.validate(ingrediente, bindingResult);
	 * 
	 * if(!bindingResult.hasErrors()) { ingredienteService.save(ingrediente);
	 * model.addAttribute("ingrediente", ingrediente);
	 * 
	 * return "ingredienteInserito.html"; }
	 * 
	 * return "ingredienteForm.html"; }
	 */

	@Transactional
	@PostMapping("/ingrediente") // String perché ritorna il nome della vista
	public String addIngrediente(@Valid @ModelAttribute("ingrediente") Ingrediente ingrediente,
			BindingResult bindingResult, Model model) {

		// ingrediente.getPiatti().add(piattoService.findById(id));
		// ingrediente.setPiatto(piattoService.findById(id));
		ingredienteValidator.validate(ingrediente, bindingResult);

		if (!bindingResult.hasErrors()) {
			ingredienteService.save(ingrediente);
			model.addAttribute("ingrediente", ingrediente);

			return "ingredienteInserito.html";
		}

		return "ingredienteForm.html";
	}
	
//	@Transactional
//	@PostMapping("/piatto/{id}/ingrediente") // String perché ritorna il nome della vista
//	public String addIngredientePiatto(@Valid @ModelAttribute("ingrediente") Ingrediente ingrediente,
//			BindingResult bindingResult, @PathVariable Long id, Model model) {
//
//		ingredienteValidator.validate(ingrediente, bindingResult);
//
//		if (!bindingResult.hasErrors()) {
//			ingredienteService.save(ingrediente);
//			
//			Piatto piatto = piattoService.findById(id);
//			piatto.getIngredienti().add(ingrediente);
//			piattoService.save(piatto);
//			
//			model.addAttribute("ingrediente", ingrediente);
//			return "ingredienteInserito.html";
//		}
//
//		return "ingredienteFormPiatto.html";
//	}

	@GetMapping("/piatto/{id}/ingredienti")
	public String getIngredientiByPiatto(@PathVariable("id") Long id, Model model) {
		List<Ingrediente> ingredienti = ingredienteService.findAllByPiatto(id);
		model.addAttribute("ingredienti", ingredienti);

		return "ingredienti.html";
	}

	@GetMapping("/ingredienti")
	public String getIngredienti(Model model) {
		List<Ingrediente> ingredienti = ingredienteService.findAll();
		model.addAttribute("ingredienti", ingredienti);

		return "ingredienti.html";
	}

	// tra parentesi graffe perché l'id è un parametro
	// @PathVariable("id") indica che il parametro Long id proviene dal parametro in
	// /ingrediente/{id}
	@GetMapping("/ingrediente/{id}")
	public String getIngrediente(@PathVariable("id") Long id, Model model) {
		Ingrediente ingrediente = ingredienteService.findById(id);
		model.addAttribute("ingrediente", ingrediente);

		return "ingrediente.html";
	}

	@GetMapping("/ingredienteForm")
	public String getIngredienteForm(Model model) {
		model.addAttribute("ingrediente", new Ingrediente());

		return "ingredienteForm.html";
	}
	
//	@GetMapping("/piatto/{id}/ingredienteForm")
//	public String getIngredienteFormPiatto(@PathVariable("id") Long id, Model model) {
//		model.addAttribute("ingrediente", new Ingrediente());
//
//		return "ingredienteFormPiatto.html";
//	}

	@GetMapping("/deleteIngrediente/{id}")
	public String deleteIngrediente(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ingrediente", this.ingredienteService.findById(id));

		return "deleteIngrediente.html";
	}

	@Transactional
	@GetMapping("/confirmDeleteIngrediente/{id}")
	public String confirmDeleteIngrediente(@PathVariable("id") Long id, Model model) {

		// bisogna prima rimuovere l'ingrediente da tutti i piatti che lo contengono e
		// poi provare a levarlo dalla base di dati (sennò dà errore)
		for (Piatto p : this.piattoService.findAll()) {
			List<Ingrediente> listToRemove = new ArrayList<Ingrediente>();
			List<Ingrediente> ingredienti = p.getIngredienti();
			for (Ingrediente i : ingredienti) {
				if (i.getId().equals(id)) {
					listToRemove.add(this.ingredienteService.findById(id));
				}
			}
			ingredienti.removeAll(listToRemove);
		}

		this.ingredienteService.deleteById(id);

		model.addAttribute("ingredienti", ingredienteService.findAll());

		return "ingredienti.html";
	}

	@GetMapping("/editIngredienteForm/{id}")
	public String editIngredienteForm(@PathVariable Long id, Model model) {
		model.addAttribute("ingrediente", this.ingredienteService.findById(id));

		return "editIngredienteForm.html";
	}

	@Transactional
	@PostMapping("/editIngrediente/{id}")
	public String editIngrediente(@Valid @ModelAttribute("ingrediente") Ingrediente ingrediente,
			BindingResult bindingResult, @PathVariable Long id, Model model) {

		Ingrediente vecchioIngrediente = this.ingredienteService.findById(id);

		if (!vecchioIngrediente.equals(ingrediente))
			this.ingredienteValidator.validate(ingrediente, bindingResult);

		if (!bindingResult.hasErrors()) {
			vecchioIngrediente.setNome(ingrediente.getNome());
			vecchioIngrediente.setDescrizione(ingrediente.getDescrizione());
			vecchioIngrediente.setOrigine(ingrediente.getOrigine());

			this.ingredienteService.save(vecchioIngrediente);
			model.addAttribute("ingrediente", vecchioIngrediente);
			return "ingredienteInserito.html";
		}

		return "editIngredienteForm.html";
	}

}