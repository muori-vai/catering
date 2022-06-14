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

import com.catering.controller.validator.ChefValidator;
import com.catering.model.Chef;
import com.catering.service.ChefService;

@Controller
public class ChefController {

	@Autowired
	private ChefService chefService;

	@Autowired
	private ChefValidator chefValidator;

	@PostMapping("/chef") // String perché ritorna il nome della vista
	public String addChef(@Valid @ModelAttribute("chef") Chef chef, BindingResult bindingResult, Model model) {

		chefValidator.validate(chef, bindingResult);

		if (!bindingResult.hasErrors()) {
			chefService.save(chef);
			model.addAttribute("chef", chef);

			return "chefInserito.html";
		}

		return "chefForm.html";
	}

	@GetMapping("/chefs")
	public String getChefs(Model model) {
		List<Chef> chefs = chefService.findAll();
		model.addAttribute("chefs", chefs);

		return "chefs.html";
	}

	// tra parentesi graffe perché l'id è un parametro
	// @PathVariable("id") indica che il parametro Long id proviene dal parametro in
	// /chef/{id}
	@GetMapping("/chef/{id}")
	public String getChef(@PathVariable("id") Long id, Model model) {
		Chef chef = chefService.findById(id);
		model.addAttribute("chef", chef);
		model.addAttribute("buffets", chef.getBuffets());

		return "chef.html";
	}

	@GetMapping("/chefForm")
	public String getChefForm(Model model) {
		model.addAttribute("chef", new Chef());

		return "chefForm.html";
	}

	@GetMapping("/deleteChef/{id}")
	public String deleteChef(@PathVariable("id") Long id, Model model) {
		model.addAttribute("chef", this.chefService.findById(id));

		return "deleteChef.html";
	}

	@Transactional
	@GetMapping("/confirmDeleteChef/{id}")
	public String confirmDeleteChef(@PathVariable("id") Long id,
			/* @RequestParam(value="action", required=true) String action, */ Model model) {
		/*
		 * if(action.equals("Elimina")) { this.chefService.deleteById(id); }
		 */

		this.chefService.deleteById(id);

		model.addAttribute("chefs", chefService.findAll());

		return "chefs.html";
	}

	@GetMapping("/editChefForm/{id}")
	public String editChefForm(@PathVariable Long id, Model model) {
		model.addAttribute("chef", chefService.findById(id));

		return "editChefForm.html";
	}

	@Transactional
	@PostMapping("/editChef/{id}")
	public String editChef(@Valid @ModelAttribute("chef") Chef chef, BindingResult bindingResult, @PathVariable Long id,
			Model model) {

		Chef vecchioChef = this.chefService.findById(id);

		if (!vecchioChef.equals(chef))
			this.chefValidator.validate(chef, bindingResult);

		if (!bindingResult.hasErrors()) {
			vecchioChef.setNome(chef.getNome());
			vecchioChef.setCognome(chef.getCognome());
			vecchioChef.setNazionalita(chef.getNazionalita());

			this.chefService.save(vecchioChef);
			model.addAttribute("chef", vecchioChef);
			return "chefInserito.html";
		}

		return "editChefForm.html";
	}
}