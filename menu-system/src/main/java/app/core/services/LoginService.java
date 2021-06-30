package app.core.services;

import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.core.entities.User;
import app.core.exceptions.MenuException;
import app.core.repositories.UserRepository;
import app.core.security.JwtUtil;
import app.core.security.PasswordUtils;
import app.core.util.UserPayload;

@Service
@Transactional
public class LoginService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	JwtUtil jwtUtil;
	public LoginService() {
	}

	public boolean isEmailExists(String email) {
		Optional<User> userOpt = userRepository.findByEmail(email);
		if (userOpt.isPresent())
			return userOpt.get().getEmail().equals(email);
		else
			return false;
	}

	public UserPayload signUp(User userDetails) throws MenuException {
		try {
			if (isEmailExists(userDetails.getEmail())) {
				throw new MenuException("Email exists already!");
			}
			String receivedPassword = userDetails.getPassword();
			String salt = PasswordUtils.getSalt(30);
			String securePassword = PasswordUtils.generateSecurePassword(receivedPassword, salt);
			userDetails.setPassword(securePassword);
			userDetails.setSalt(salt);
			User user = userRepository.save(userDetails);
			String token = jwtUtil.generateToken(user.getEmail(), securePassword, user.getLevel(),
					user.getId());
			UserPayload userPayload = new UserPayload(user.getId(), user.getName(), user.getAddress(),
					user.getPhone(), user.getLevel(),user.getBranch(), user.getEmail(), token);
			return userPayload;
		} catch (Exception e) {
			throw new MenuException(e.getLocalizedMessage());
		}
	}

	public UserPayload signIn(String email, String password) throws MenuException {
		try {
			Optional<User> userOpt = userRepository.findByEmail(email);
			if(userOpt.isEmpty()) {
				throw new MenuException("Login failed!");
			}
			User user = userOpt.get();
			String securePassword = user.getPassword();
			String salt = user.getSalt();
			boolean passwordMatch = PasswordUtils.verifyUserPassword(password, securePassword, salt);
			if (passwordMatch) {
				String token = jwtUtil.generateToken(user.getEmail(), securePassword, user.getLevel(), user.getId());
				UserPayload userPayload = new UserPayload(user.getId(), user.getName(), user.getAddress(),
						user.getPhone(), user.getLevel(), user.getBranch(),user.getEmail(), token);
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
