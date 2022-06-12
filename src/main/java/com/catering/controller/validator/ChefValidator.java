package com.catering.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.catering.model.Chef;
import com.catering.service.ChefService;

@Component
public class ChefValidator implements Validator {

	@Autowired
	ChefService chefService;
	
	@Override
	public boolean supports(Class<?> chefClass) {
		return Chef.class.equals(chefClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if(this.chefService.alreadyExists((Chef) target)) {
			errors.reject("chef.duplicato");
		}
	}
}
