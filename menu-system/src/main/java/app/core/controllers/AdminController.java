package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Branch;
import app.core.entities.Dish;
import app.core.entities.User;
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
		try {
			if (jwtUtil.isTokenExpired(token)) {

				throw new MenuException("You are not authorized");
			}
			return adminService;
		} catch (Exception e) {
			throw new MenuException("You are not authorized");
		}
	}

	
	@GetMapping("/getBranches")
	public List<Branch> getBranches(@RequestHeader String token) {
		try {
			return getAdmin(token).getBranches();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getLocalizedMessage());
		}
	}

	@GetMapping("/getUsers")
	public List<User> getUsers(@RequestHeader String token) {
		try {
			return getAdmin(token).getUsers();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getLocalizedMessage());
		}
	}
	
	@GetMapping("/getBranch/{id}")
	public Branch getBranche(@RequestHeader String token, @PathVariable int id) {
		try {
			return getAdmin(token).getBranch(id);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getLocalizedMessage());
		}
	}
		
	@PostMapping(path = "/addDish", consumes = { "multipart/form-data" })
	public Dish addDish(@RequestHeader String token, @ModelAttribute DishPayload payload) {
		try {
			return getAdmin(token).addDish(payload);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getLocalizedMessage());
		}
	}
	@PostMapping(path = "/addBranch")
	public Branch addBranch(@RequestHeader String token, @RequestBody Branch branch) {
		try {
			return getAdmin(token).addBranch(branch);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getLocalizedMessage());
		}
	}
	@PostMapping(path = "/addUser/{branchId}")
	public User addUser(@RequestHeader String token, @RequestBody User user, @PathVariable int branchId) {
		Branch branch = new Branch();
		branch.setId(branchId);
		user.setBranch(branch);
		try {
			return getAdmin(token).addUser(user);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getLocalizedMessage());
		}
	}
	@PutMapping(path = "/updateUser/{branchId}")
	public User updateUser(@RequestHeader String token, @RequestBody User user, @PathVariable int branchId) {
		Branch branch = new Branch();
		branch.setId(branchId);
		user.setBranch(branch);
		try {
			return getAdmin(token).updateUser(user);
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
	@PutMapping(path = "/updateBranch")
	public Branch updateBranch(@RequestHeader String token, @RequestBody Branch branch) {
		try {
			return getAdmin(token).updateBranch(branch);
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
	@DeleteMapping("/deleteBranch/{id}")
	public String deleteBranch(@RequestHeader String token, @PathVariable int id) {
		try {
			getAdmin(token).deleteBranch(id);
			return "DELETED";
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getLocalizedMessage());
		}
	}
	@DeleteMapping("/deleteUser/{id}")
	public String deleteUser(@RequestHeader String token, @PathVariable int id) {
		try {
			getAdmin(token).deleteUser(id);
			return "DELETED";
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getLocalizedMessage());
		}
	}

}
