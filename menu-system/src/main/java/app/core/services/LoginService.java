package app.core.services;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.core.entities.Customer;
import app.core.exceptions.MenuException;
import app.core.repositories.CustomerRepository;
import app.core.securirty.PasswordUtils;
import app.core.security.JwtUtil;


@Service
@Transactional
public class LoginService {
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	JwtUtil jwtUtil;
	
	public LoginService() {
	}
	
	public boolean isEmailExists(String email) {
		Customer user = customerRepository.findByEmail(email);
		if (user!=null)
			return user.getEmail().equals(email);
		else 
			return false;
	}
	
	public String signUp(Customer userDetails) throws MenuException{
		if (isEmailExists(userDetails.getEmail())) {
			throw new MenuException("Email exists already!");
		}
		String receivedPassword = userDetails.getPassword();
		String salt = PasswordUtils.getSalt(30);
		String securePassword = PasswordUtils.generateSecurePassword(receivedPassword, salt);
		userDetails.setPassword(securePassword);
		userDetails.setSalt(salt);
		int id = customerRepository.save(userDetails).getId();
		return jwtUtil.generateToken(userDetails.getEmail(), securePassword, userDetails.getLevel(), id);
	}
	
	public String signIn(String email, String password) throws MenuException{
		Customer user = customerRepository.findByEmail(email);
		String securePassword = user.getPassword();
		String salt = user.getSalt();
		boolean passwordMatch = PasswordUtils.verifyUserPassword(password, securePassword, salt);
		if (passwordMatch) {
			return jwtUtil.generateToken(user.getEmail(), securePassword, user.getLevel(), user.getId());
		} else {
			throw new MenuException("Login failed!");
		}
	}
	
	public void signOut (String token) {
		System.out.println("signedout");
	}
}
