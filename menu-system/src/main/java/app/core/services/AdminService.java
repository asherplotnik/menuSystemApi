package app.core.services;

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

import app.core.entities.Dish;
import app.core.enums.Category;
import app.core.exceptions.MenuException;
import app.core.repositories.UserRepository;
import app.core.repositories.DishRepository;
import app.core.repositories.MenuEntryRepository;
import app.core.repositories.MenuOrderRepository;
import app.core.security.JwtUtil;
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
	UserRepository userRepository;
	@Value("${number.of.tables:10}")
	int numberOfTables;
	@Value("${imgbb.api.key}")
	private String imgbbApiKey;

	
	public Dish addDish(DishPayload payload) throws MenuException {
		try {
			Dish dish = new Dish();
			dish.setAvailable(payload.getAvailable()==null?false:true);
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
			dish.setAvailable(payload.getAvailable()==null?false:true);
			dish.setCategory(Category.valueOf(payload.getCategory()));
			dish.setDescription(payload.getDescription());
			dish.setPrice(Double.parseDouble(payload.getPrice()));
			dish.setName(payload.getName());
			String imageUrl = uploadImageToImgbb(payload.getImage1());
			if (imageUrl!= null)
				dish.setPrimaryImage(imageUrl);
			imageUrl = uploadImageToImgbb(payload.getImage2());
			if (imageUrl!= null)
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
}
