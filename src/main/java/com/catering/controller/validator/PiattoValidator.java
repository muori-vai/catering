package com.catering.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.catering.model.Piatto;
import com.catering.service.PiattoService;

@Component
public class PiattoValidator implements Validator {

	@Autowired
	PiattoService piattoService;
	
	@Override
	public boolean supports(Class<?> piattoClass) {
		return Piatto.class.equals(piattoClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if(this.piattoService.alreadyExists((Piatto) target)) {
			errors.reject("piatto.duplicato");
		}
	}
}
