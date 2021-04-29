package app.core.services;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.core.entities.Customer;
import app.core.exceptions.MenuException;
import app.core.repositories.CustomerRepository;
import app.core.security.JwtUtil;
import app.core.security.PasswordUtils;
import app.core.util.UserPayload;

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
		if (user != null)
			return user.getEmail().equals(email);
		else
			return false;
	}

	public UserPayload signUp(Customer userDetails) throws MenuException {
		try {
			if (isEmailExists(userDetails.getEmail())) {
				throw new MenuException("Email exists already!");
			}
			String receivedPassword = userDetails.getPassword();
			String salt = PasswordUtils.getSalt(30);
			String securePassword = PasswordUtils.generateSecurePassword(receivedPassword, salt);
			userDetails.setPassword(securePassword);
			userDetails.setSalt(salt);
			Customer customer = customerRepository.save(userDetails);
			String token = jwtUtil.generateToken(customer.getEmail(), securePassword, customer.getLevel(),
					customer.getId());
			UserPayload userPayload = new UserPayload(customer.getId(), customer.getName(), customer.getAddress(),
					customer.getPhone(), customer.getLevel(), customer.getEmail(), token);
			return userPayload;
		} catch (Exception e) {
			throw new MenuException(e.getLocalizedMessage());
		}
	}

	public UserPayload signIn(String email, String password) throws MenuException {
		try {
			Customer user = customerRepository.findByEmail(email);
			String securePassword = user.getPassword();
			String salt = user.getSalt();
			boolean passwordMatch = PasswordUtils.verifyUserPassword(password, securePassword, salt);
			if (passwordMatch) {
				String token = jwtUtil.generateToken(user.getEmail(), securePassword, user.getLevel(), user.getId());
				UserPayload userPayload = new UserPayload(user.getId(), user.getName(), user.getAddress(),
						user.getPhone(), user.getLevel(), user.getEmail(), token);
				return userPayload;
			} else {
				throw new MenuException("Login failed!");
			}
		} catch (Exception e) {
			throw new MenuException(e.getLocalizedMessage());
		}
	}

	public void signOut(String token) {
		System.out.println("signedout");
	}
}
