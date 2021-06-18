package app.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import app.core.entities.User;
import app.core.exceptions.MenuException;
import app.core.services.LoginService;
import app.core.util.SignInDetails;
import app.core.util.UserPayload;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {
	@Autowired
	LoginService loginService;

	@PostMapping("/signUp")
	public UserPayload signUp(@RequestBody User userDetails) {
		try {
			return loginService.signUp(userDetails);
		} catch (MenuException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,e.getLocalizedMessage());
		}
	}
	
	@PostMapping("/signIn")
	public UserPayload signIn(@RequestBody SignInDetails signInDetails) {
		try {
			return loginService.signIn(signInDetails.email,signInDetails.password);
		} catch (MenuException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,e.getLocalizedMessage());
		}
	}
	
	@PostMapping("/signOut")
	public String signOut(@RequestHeader String token) {
		loginService.signOut(token);
		return "Signed Out";
	}
	
}



