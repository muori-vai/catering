package com.catering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.catering.controller.validator.CredentialsValidator;
import com.catering.controller.validator.UserValidator;
import com.catering.model.Credentials;
import com.catering.model.User;
import com.catering.service.CredentialsService;

@Controller
public class AuthenticationController {

	@Autowired
	private CredentialsService credentialsService;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private CredentialsValidator credentialsValidator;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showRegisterForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "registerUser";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginForm(Model model) {
		return "loginForm";
	}

	@RequestMapping(value = "/login-error", method = RequestMethod.GET)
	public String showLoginFormError(Model model) {
		return "loginFormError";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model) {
		// ho aggiunto questo perché senza il logout funziona male
		// (invalidateHttpSession = true (di AuthConfiguration) non va!), non so per
		// quale motivo
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		model.addAttribute("nome", credentials.getUser().getNome());
		
		SecurityContextHolder.getContext().setAuthentication(null);
		return "goodbye";
	}
	
	@RequestMapping(value = "/askLogout", method = RequestMethod.GET)
	public String showLogout(Model model) {
		return "askLogout.html";
	}

	@RequestMapping(value = "/default", method = RequestMethod.GET)
	public String defaultAfterLogin(Model model) {

//  	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
//  	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
//            return "admin/home";
//      }

		/* Non funziona */
//    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication();
//    	if (userDetails == null)
//    	{
//    		return "loginFormError";
//    	}

		return "home";
	}

	@RequestMapping(value = { "/register" }, method = RequestMethod.POST)
	public String registerUser(@ModelAttribute("user") User user, BindingResult userBindingResult,
			@ModelAttribute("credentials") Credentials credentials, BindingResult credentialsBindingResult,
			Model model) {

		// validate user and credentials fields
		this.userValidator.validate(user, userBindingResult);
		this.credentialsValidator.validate(credentials, credentialsBindingResult);

		// if neither of them had invalid contents, store the User and the Credentials
		// into the DB
		if (!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
			// set the user and store the credentials;
			// this also stores the User, thanks to Cascade.ALL policy
			credentials.setUser(user);
			credentialsService.saveCredentials(credentials);
			return "registrationSuccessful";
		}
		return "registerUser";
	}
}
