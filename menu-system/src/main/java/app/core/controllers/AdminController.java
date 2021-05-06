package app.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import app.core.entities.Dish;
import app.core.exceptions.MenuException;
import app.core.security.JwtUtil;
import app.core.services.AdminService;
import app.core.services.OrderService;
import app.core.util.DishPayload;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {
	@Autowired
	AdminService adminService;
	@Autowired
	OrderService orderService;
	@Autowired
	JwtUtil jwtUtil;

	private AdminService getAdmin(String token) throws MenuException {
		if (jwtUtil.isTokenExpired(token)) {
			throw new MenuException("You are not authorized");
		}
		return adminService;
	}

	@PostMapping(path = "/addDish", consumes = { "multipart/form-data" })
	public Dish addDish(@RequestHeader String token, @ModelAttribute DishPayload payload) {
		try {
			return getAdmin(token).addDish(payload);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getLocalizedMessage());
		}
	}

	@PutMapping(path = "/updateDish", consumes = { "multipart/form-data" })
	public Dish updateDish(@RequestHeader String token, @ModelAttribute DishPayload payload) {
		try {
			return getAdmin(token).updateDish(payload);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getLocalizedMessage());
		}
	}
	
	@DeleteMapping("/deleteDish/{id}")
	public String deleteDish(@RequestHeader String token, @PathVariable int id) {
		try {
			getAdmin(token).deleteDish(id);
			return "DELETED";
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getLocalizedMessage());
		}
	}
	
}
