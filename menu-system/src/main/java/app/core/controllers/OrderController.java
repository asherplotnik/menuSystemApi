package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import app.core.entities.Dish;
import app.core.entities.MenuOrder;
import app.core.exceptions.MenuException;
import app.core.security.JwtUtil;
import app.core.services.OrderService;
import app.core.util.OrderPayload;

@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController {
	@Autowired
	OrderService orderService;	
	@Autowired
	JwtUtil jwtUtil;
		
	private OrderService getService(String token) throws MenuException {
		try {
			if (jwtUtil.isTokenExpired(token)) {
				throw new MenuException("You are not logged in !!!");
			}
			return orderService;
		} catch (Exception e) {
			throw new MenuException("You are not logged in !!!");
		}
	}

	@GetMapping("/getMenu")
	public List<Dish> getMemu() {
		try {
			return orderService.getMenu();
		} catch (MenuException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getLocalizedMessage());
		}

	}

	@PostMapping("/makeOrder")
	public MenuOrder makeOrder(@RequestHeader String token, @RequestBody OrderPayload orderPayload) {
		try {
			return getService(token).sendNewOrder(token, orderPayload);
		} catch (MenuException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getLocalizedMessage());
		}
	}
	

}
