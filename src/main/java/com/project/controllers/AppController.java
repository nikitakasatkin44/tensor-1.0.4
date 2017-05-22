//package com.project.controllers;
//
//import com.project.model.User;
//import com.project.model.UserProfile;
//import com.project.service.UserProfileService;
//import com.project.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
//import org.springframework.security.authentication.AuthenticationTrustResolver;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.validation.Valid;
//import java.util.List;
//import java.util.Locale;
//
//
//@Controller
//@RequestMapping("/")
//@SessionAttributes("roles")
//public class AppController {
//
//	@Autowired
//    UserService userService;
//
//	@Autowired
//    UserProfileService userProfileService;
//
//	@Autowired
//    MessageSource messageSource;
//
//	@Autowired
//	PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;
//
//	@Autowired
//	AuthenticationTrustResolver authenticationTrustResolver;
//
//
//	@RequestMapping(value = {"/", "/index" }, method = RequestMethod.GET)
//	public String index(ModelMap model) {
//
//		model.addAttribute("loggedinuser", getPrincipal());
//
//		return "index";
//	}
//
//	@RequestMapping(value = { "/outside-registration" }, method = RequestMethod.GET)
//	public String outsideRegistration(ModelMap model) {
//		User user = new User();
//		model.addAttribute("user", user);
//		model.addAttribute("edit", false);
//		model.addAttribute("loggedinuser", getPrincipal());
//		return "outside-registration";
//	}
//
//	@RequestMapping(value = { "/outside-registration" }, method = RequestMethod.POST)
//	public String outsideRegistrationSave(@Valid User user, BindingResult result,
//                                          ModelMap model) {
//
//		if (result.hasErrors()) {
//			System.out.println(result.getAllErrors());
//			return "outside-registration";
//		}
//
//		if(!userService.isUserSSOUnique(user.getId(), user.getSsoId())){
//			FieldError ssoError =new FieldError("user","ssoId",messageSource.getMessage("non.unique.ssoId", new String[]{user.getSsoId()}, Locale.getDefault()));
//			result.addError(ssoError);
//			return "outside-registration";
//		}
//
//		userService.saveUser(user);
//
//		model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " registered successfully");
//		model.addAttribute("loggedinuser", getPrincipal());
//
//		return "registrationsuccess";
//	}
//
//	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
//	public String listUsers(ModelMap model) {
//
//		List<User> users = userService.findAllUsers();
//		model.addAttribute("users", users);
//		model.addAttribute("loggedinuser", getPrincipal());
//		return "userslist";
//	}
//
//	@RequestMapping(value = { "/newuser" }, method = RequestMethod.GET)
//	public String newUser(ModelMap model) {
//		User user = new User();
//		model.addAttribute("user", user);
//		model.addAttribute("edit", false);
//		model.addAttribute("loggedinuser", getPrincipal());
//		return "registration";
//	}
//
//	@RequestMapping(value = { "/newuser" }, method = RequestMethod.POST)
//	public String saveUser(@Valid User user, BindingResult result,
//                           ModelMap model) {
//
//		if (result.hasErrors()) {
//			return "registration";
//		}
//
//		if(!userService.isUserSSOUnique(user.getId(), user.getSsoId())){
//			FieldError ssoError =new FieldError("user","ssoId",messageSource.getMessage("non.unique.ssoId", new String[]{user.getSsoId()}, Locale.getDefault()));
//		    result.addError(ssoError);
//			return "registration";
//		}
//
//		userService.saveUser(user);
//
//		model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " registered successfully");
//		model.addAttribute("loggedinuser", getPrincipal());
//		//return "success";
//		return "registrationsuccess";
//	}
//
//	@RequestMapping(value = { "/edit-user-{ssoId}" }, method = RequestMethod.GET)
//	public String editUser(@PathVariable String ssoId, ModelMap model) {
//		User user = userService.findBySSO(ssoId);
//		model.addAttribute("user", user);
//		model.addAttribute("edit", true);
//		model.addAttribute("loggedinuser", getPrincipal());
//		return "registration";
//	}
//
//	@RequestMapping(value = { "/edit-user-{ssoId}" }, method = RequestMethod.POST)
//	public String updateUser(@Valid User user, BindingResult result,
//                             ModelMap model, @PathVariable String ssoId) {
//
//		if (result.hasErrors()) {
//			return "registration";
//		}
//
//		/*//Uncomment below 'if block' if you WANT TO ALLOW UPDATING SSO_ID in UI which is a unique key to a User.
//		if(!userService.isUserSSOUnique(user.getId(), user.getSsoId())){
//			FieldError ssoError =new FieldError("user","ssoId",messageSource.getMessage("non.unique.ssoId", new String[]{user.getSsoId()}, Locale.getDefault()));
//		    result.addError(ssoError);
//			return "registration";
//		}*/
//
//
//		userService.updateUser(user);
//
//		model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " updated successfully");
//		model.addAttribute("loggedinuser", getPrincipal());
//		return "registrationsuccess";
//	}
//
//	@RequestMapping(value = { "/delete-user-{ssoId}" }, method = RequestMethod.GET)
//	public String deleteUser(@PathVariable String ssoId) {
//		userService.deleteUserBySSO(ssoId);
//		return "redirect:/list";
//	}
//
//	@ModelAttribute("roles")
//	public List<UserProfile> initializeProfiles() {
//		return userProfileService.findAll();
//	}
//
//	@RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
//	public String accessDeniedPage(ModelMap model) {
//		model.addAttribute("loggedinuser", getPrincipal());
//		return "accessDenied";
//	}
//
//	@RequestMapping(value = "/login", method = RequestMethod.GET)
//	public String loginPage() {
//		if (isCurrentAuthenticationAnonymous()) {
//			return "login";
//	    } else {
//	    	return "redirect:/index";
//	    }
//	}
//
//	@RequestMapping(value="/logout", method = RequestMethod.GET)
//	public String logoutPage (HttpServletRequest request, HttpServletResponse response){
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		if (auth != null){
//			//new SecurityContextLogoutHandler().logout(request, response, auth);
//			persistentTokenBasedRememberMeServices.logout(request, response, auth);
//			SecurityContextHolder.getContext().setAuthentication(null);
//		}
//		return "redirect:/login?logout";
//	}
//
//	private String getPrincipal(){
//		String userName = null;
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//		if (principal instanceof UserDetails) {
//			userName = ((UserDetails)principal).getUsername();
//		} else {
//			userName = principal.toString();
//		}
//		return userName;
//	}
//
//	private boolean isCurrentAuthenticationAnonymous() {
//	    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//	    return authenticationTrustResolver.isAnonymous(authentication);
//	}
//
//	@RequestMapping(value="/about", method = RequestMethod.GET)
//	public String aboutPage(ModelMap model) {
//		model.addAttribute("user", getPrincipal());
//		return "about";
//	}
//
//	@RequestMapping(value="/contacts", method = RequestMethod.GET)
//	public String contactsPage(ModelMap model) {
//		model.addAttribute("user", getPrincipal());
//		return "contacts";
//	}
//
//	@RequestMapping(value="/send", method = RequestMethod.GET)
//	public String sendMessagePage(ModelMap model) {
//
//		String testMessage = "HELLO DOTA!";
//
//
//
//		return "send";
//	}
//
//
//}