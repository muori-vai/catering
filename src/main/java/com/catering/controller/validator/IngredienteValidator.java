package com.catering.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.catering.model.Ingrediente;
import com.catering.service.IngredienteService;

@Component
public class IngredienteValidator implements Validator {

	@Autowired
	IngredienteService ingredienteService;
	
	@Override
	public boolean supports(Class<?> ingredienteClass) {
		return Ingrediente.class.equals(ingredienteClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if(this.ingredienteService.alreadyExists((Ingrediente) target)) {
			errors.reject("ingrediente.duplicato");
		}
	}
}
