package com.catering.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.catering.model.Buffet;
import com.catering.service.BuffetService;

@Component
public class BuffetValidator implements Validator {

	@Autowired
	BuffetService buffetService;
	
	@Override
	public boolean supports(Class<?> buffetClass) {
		return Buffet.class.equals(buffetClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if(this.buffetService.alreadyExists((Buffet) target)) {
			errors.reject("buffet.duplicato");
		}
	}
}
