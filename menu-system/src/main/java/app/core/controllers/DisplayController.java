package app.core.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import app.core.entities.MenuOrder;
import app.core.enums.Level;
import app.core.enums.Status;
import app.core.exceptions.MenuException;
import app.core.security.JwtUtil;
import app.core.services.OrderService;

@RestController
@RequestMapping("/display")
@CrossOrigin
public class DisplayController {
	@Autowired
	OrderService orderService;
	@Autowired
	JwtUtil jwtUtil;

	private OrderService getService(String token, Level level) throws MenuException {
		try {
			if (jwtUtil.isTokenExpired(token)) {
				throw new MenuException("You are not logged in !!!");
			}
			if (!jwtUtil.extractUserType(token).equals(level.toString())
					&& !jwtUtil.extractUserType(token).equals(Level.ADMIN.toString())) {
				throw new MenuException("You are not Authorized !!!");
			}
			return orderService;
		} catch (Exception e) {
			throw new MenuException(e.getLocalizedMessage());
		}
	}

	@GetMapping("/getOrdersByStatus/{status}")
	public List<MenuOrder> getReadyOrders(@RequestHeader String token, @PathVariable String status) {
		try {
			Status stat = Status.valueOf(status);
			switch (stat) {
				case ORDERED:
					return getService(token, Level.KITCHEN).getOrdersByStatus(stat,token);
				case READY:
				case SERVED:
					return getService(token, Level.SERVICE).getOrdersByStatus(stat,token);
				case PAID:
					return getService(token,Level.SERVICE).getOrdersByStatus(stat,token);
				default:
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "STATUS INVALID!!!");
			}
		} catch (MenuException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getLocalizedMessage());
		}
	}

	@PostMapping("/updateEntryStatus/{entryId}")
	public LocalDateTime updateOrderEntry(@RequestHeader String token, @PathVariable int entryId) {
		try {
			return getService(token, Level.KITCHEN).updateEntryStatus(entryId);
		} catch (MenuException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getLocalizedMessage());
		}
	}

	@PostMapping("/updateOrderStatus/{orderId}/{status}")
	public MenuOrder updateOrderReady(@RequestHeader String token, @PathVariable int orderId,
			@PathVariable String status) {
		try {
			Status stat = Status.valueOf(status);
			return getService(token, Level.KITCHEN).updateOrderStatus(orderId, stat);
		} catch (MenuException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getLocalizedMessage());
		}
	}

}
