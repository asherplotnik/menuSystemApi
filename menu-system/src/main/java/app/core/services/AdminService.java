package app.core.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import app.core.entities.Branch;
import app.core.entities.Dish;
import app.core.entities.User;
import app.core.enums.Category;
import app.core.enums.Level;
import app.core.exceptions.MenuException;
import app.core.repositories.UserRepository;
import app.core.repositories.BranchRepository;
import app.core.repositories.DishRepository;
import app.core.repositories.MenuEntryRepository;
import app.core.repositories.MenuOrderRepository;
import app.core.security.JwtUtil;
import app.core.security.PasswordUtils;
import app.core.util.DishPayload;

@Service
@Transactional
public class AdminService {
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	MenuOrderRepository menuOrderRepository;
	@Autowired
	MenuEntryRepository menuEntryRepository;
	@Autowired
	DishRepository dishRepository;
	@Autowired
	BranchRepository branchRepository;
	@Autowired
	UserRepository userRepository;
	@Value("${number.of.tables:10}")
	int numberOfTables;
	@Value("${imgbb.api.key}")
	private String imgbbApiKey;

	public Dish addDish(DishPayload payload) throws MenuException {
		try {
			Dish dish = new Dish();
			dish.setAvailable(payload.getAvailable() == null ? false : true);
			dish.setCategory(Category.valueOf(payload.getCategory()));
			dish.setDescription(payload.getDescription());
			dish.setPrice(Double.parseDouble(payload.getPrice()));
			dish.setName(payload.getName());
			dish.setPrimaryImage(uploadImageToImgbb(payload.getImage1()));
			dish.setSecondaryImage(uploadImageToImgbb(payload.getImage2()));
			return dishRepository.save(dish);
		} catch (Exception e) {
			throw new MenuException("Add Dish failed");
		}
	}

	public Dish updateDish(DishPayload payload) throws MenuException {
		try {
			Optional<Dish> opt = dishRepository.findById(payload.getId());
			if (opt.isEmpty()) {
				throw new MenuException("Update Dish failed - not dound");
			}
			Dish dish = opt.get();
			dish.setAvailable(payload.getAvailable() == null ? false : true);
			dish.setCategory(Category.valueOf(payload.getCategory()));
			dish.setDescription(payload.getDescription());
			dish.setPrice(Double.parseDouble(payload.getPrice()));
			dish.setName(payload.getName());
			String imageUrl = uploadImageToImgbb(payload.getImage1());
			if (imageUrl != null)
				dish.setPrimaryImage(imageUrl);
			imageUrl = uploadImageToImgbb(payload.getImage2());
			if (imageUrl != null)
				dish.setSecondaryImage(imageUrl);
			return dish;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MenuException("Update Dish failed. ");
		}
	}

	public void deleteDish(int id) throws MenuException {
		try {
			dishRepository.deleteById(id);
		} catch (Exception e) {
			throw new MenuException("Delete Failed");
		}
	}

	private String uploadImageToImgbb(MultipartFile image) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
			body.add("image", image.getResource());
			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
			String serverUrl = "https://api.imgbb.com/1/upload?key=" + imgbbApiKey;
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
			String json = response.getBody();
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
			JSONObject data = (JSONObject) jsonObject.get("data");
			return (String) data.get("url");
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}

	public Branch addBranch(Branch branch) throws MenuException {
		try {
			return branchRepository.save(branch);
		} catch (Exception e) {
			throw new MenuException("Add branch Failed");
		}
	}

	public Branch updateBranch(Branch branch) throws MenuException {
		try {
			Optional<Branch> opt = branchRepository.findById(branch.getId());
			if (opt.isEmpty()) {
				throw new MenuException("Update Dish failed - not dound");
			}
			Branch newBranch = opt.get();
			newBranch.setAddress(branch.getAddress());
			newBranch.setName(branch.getName());
			return newBranch;
		} catch (Exception e) {
			throw new MenuException("Update Dish failed");
		}
	}

	public void deleteBranch(int id) throws MenuException {
		try {
			branchRepository.deleteById(id);
		} catch (Exception e) {
			throw new MenuException("Delete Failed");
		}
	}
	
	public void deleteUser(int id) throws MenuException {
		try {
			userRepository.deleteById(id);
		} catch (Exception e) {
			throw new MenuException("Delete Failed");
		}
	}

	public List<Branch> getBranches() throws MenuException {
		try {
			return branchRepository.findAll();
		} catch (Exception e) {
			throw new MenuException("get all branches Failed");
		}

	}
	
	public List<User> getUsers() throws MenuException {
		try {
			return userRepository.findAll();
		} catch (Exception e) {
			throw new MenuException("get all users Failed");
		}
		
	}

	public Branch getBranch(int id) throws MenuException {
		Optional<Branch> branch = branchRepository.findById(id);
		if (branch.isEmpty()) {
			throw new MenuException("branch not found");
		}
		return branch.get();
	}
	
	public User addUser(User user) throws MenuException {
		try {
			if (user.getLevel() == Level.ADMIN) {
				user.setBranch(null);
			} else {
				Optional<Branch> branch = branchRepository.findById(user.getBranch().getId());
				if (branch.isEmpty()) {
					throw new MenuException("Update user failed - invalid branch");
				}
				user.setBranch(branch.get());
			}
		
			if (user.getLevel() != Level.CUSTOMER) {
				user.setAddress(null);
				user.setPhone(null);
			}
			user.setSalt(PasswordUtils.getSalt(30));
			String pass = user.getPassword();
	    	user.setPassword(PasswordUtils.generateSecurePassword(pass, user.getSalt()));
			return userRepository.save(user);
		} catch(Exception e) {
			throw new MenuException("Add user failed");
		}
	}
	public User updateUser(User user) throws MenuException {
		try {
			Optional<User> userOpt = userRepository.findById(user.getId());
			if (userOpt.isEmpty()) {
				throw new MenuException("Update user failed - invalid user Id");
			}
			User newUser = userOpt.get();
			if (user.getLevel() == Level.ADMIN) {
				newUser.setBranch(null);
			} else {
				Optional<Branch> branch = branchRepository.findById(user.getBranch().getId());
				if (branch.isEmpty()) {
					throw new MenuException("Update user failed - invalid branch");
				}
				newUser.setBranch(branch.get());
			}
			newUser.setAddress(user.getAddress());
			newUser.setPhone(user.getPhone());
			newUser.setEmail(user.getEmail());
			newUser.setLevel(user.getLevel());
			newUser.setName(user.getName());
			if (newUser.getLevel() != Level.CUSTOMER) {
				newUser.setAddress(null);
				newUser.setPhone(null);
			}
			String pass = user.getPassword();
			newUser.setPassword(PasswordUtils.generateSecurePassword(pass, newUser.getSalt()));
			return newUser;
		} catch(Exception e) {
			e.printStackTrace();
			throw new MenuException("Update user failed");
		}
	}

}
