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

import com.catering.controller.validator.BuffetValidator;
import com.catering.model.Buffet;
import com.catering.service.BuffetService;
import com.catering.service.ChefService;

@Controller
public class BuffetController {

	@Autowired
	private BuffetService buffetService;

	@Autowired
	private ChefService chefService;

	@Autowired
	private BuffetValidator buffetValidator;

	@PostMapping("/chef/{id}/buffet") // String perché ritorna il nome della vista
	public String addBuffet(@Valid @ModelAttribute("buffet") Buffet buffet, BindingResult bindingResult,
			/* posso omettere il nome ("id") se è uguale */ @PathVariable Long id, Model model) {

		buffet.setChef(chefService.findById(id));
		buffetValidator.validate(buffet, bindingResult);

		if (!bindingResult.hasErrors()) {
			buffetService.save(buffet);
			model.addAttribute("buffet", buffet);
			return "buffetInserito.html";
		}

		return "buffetForm.html";
	}

	@GetMapping("chef/{id}/buffets")
	public String getBuffets(@PathVariable("id") Long id, Model model) {
		List<Buffet> buffets = buffetService.findAllByChef(id);
		model.addAttribute("buffets", buffets);

		return "buffets.html";
	}

	@GetMapping("/buffets")
	public String getBuffets(Model model) {
		List<Buffet> buffets = buffetService.findAll();
		model.addAttribute("buffets", buffets);

		return "buffets.html";
	}

	// tra parentesi graffe perché l'id è un parametro
	// @PathVariable("id") indica che il parametro Long id proviene dal parametro in
	// /buffet/{id}
	@GetMapping("/buffet/{id}")
	public String getBuffet(@PathVariable("id") Long id, Model model) {
		Buffet buffet = buffetService.findById(id);
		model.addAttribute("buffet", buffet);
		model.addAttribute("piatti", buffet.getPiatti());

		return "buffet.html";
	}

	@GetMapping("/chef/{id}/buffetForm")
	public String getBuffetForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("buffet", new Buffet());

		return "buffetForm.html";
	}

	@GetMapping("/deleteBuffet/{id}")
	public String deleteBuffet(@PathVariable("id") Long id, Model model) {
		model.addAttribute("buffet", this.buffetService.findById(id));

		return "deleteBuffet.html";
	}

	@GetMapping("/confirmDeleteBuffet/{id}")
	public String confirmDeleteBuffet(@PathVariable("id") Long id,
			/* @RequestParam(value="action", required=true) String action, */ Model model) {
		/*
		 * if(action.equals("Elimina")) { this.buffetService.deleteById(id); }
		 */

		this.buffetService.deleteById(id);

		model.addAttribute("buffets", buffetService.findAll());

		return "buffets.html";
	}

	@GetMapping("/editBuffetForm/{id}")
	public String editBuffetForm(@PathVariable Long id, Model model) {
		model.addAttribute("buffet", this.buffetService.findById(id));

		return "editBuffetForm.html";
	}

	@Transactional
	@PostMapping("/editBuffet/{id}")
	public String editBuffet(@Valid @ModelAttribute("buffet") Buffet buffet, BindingResult bindingResult,
			@PathVariable Long id, Model model) {

		Buffet vecchioBuffet = this.buffetService.findById(id);

		//model.addAttribute("idChef", vecchioBuffet.getChef().getId());

		if (!vecchioBuffet.equals(buffet))
			this.buffetValidator.validate(buffet, bindingResult);

		if (!bindingResult.hasErrors()) {
			vecchioBuffet.setNome(buffet.getNome());
			vecchioBuffet.setDescrizione(buffet.getDescrizione());

			this.buffetService.save(vecchioBuffet);
			model.addAttribute("buffet", vecchioBuffet);
			return "buffetModificato.html";
		}

		return "editBuffetForm.html";
	}
}