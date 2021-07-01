package app.core.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import app.core.entities.Branch;
import app.core.entities.User;
import app.core.entities.Dish;
import app.core.enums.Category;
import app.core.enums.Level;
import app.core.repositories.BranchRepository;
import app.core.repositories.UserRepository;
import app.core.repositories.DishRepository;
import app.core.security.PasswordUtils;

@Service
@Transactional
public class CreateDatabaseService {
	
	@Autowired
	BranchRepository branchRepository;
	
	@Autowired
	DishRepository dishRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Value("${number.of.tables:10}")
	int numberOfTables;
	
	public void createDatabase() {
		Branch branch1 = new Branch("Main branch", "Main street 1");
		branchRepository.save(branch1);
		Branch branch2 = new Branch("Second branch", "Second street 1");
		branchRepository.save(branch2);
		Dish dish = new Dish();
		dish.setCategory(Category.DRINK);
		dish.setDescription("Mineral water bottle");
		dish.setName("Water");
		dish.setPrice(3);
		dish.setPrimaryImage("https://i.ibb.co/vjhkhkj/water2.jpg");
		dish.setSecondaryImage("https://i.ibb.co/vjhkhkj/water2.jpg");
		dishRepository.save(dish);
		dish = new Dish();
		dish.setCategory(Category.DRINK);
		dish.setDescription("coca cola bottle");
		dish.setName("coke");
		dish.setPrice(5);
		dish.setPrimaryImage("https://i.ibb.co/hXpr4s5/coke2.jpg");
		dish.setSecondaryImage("https://i.ibb.co/hXpr4s5/coke2.jpg");
		dishRepository.save(dish);
		dish = new Dish();
		dish.setCategory(Category.MAIN_COURSE);
		dish.setDescription("Pasta Bolonese with ground beed and tomato sauce");
		dish.setName("Pasta Bolonese");
		dish.setPrice(18);
		dish.setPrimaryImage("https://i.ibb.co/ZLCMGnV/bolonese2.jpg");
		dish.setSecondaryImage("https://i.ibb.co/ZLCMGnV/bolonese2.jpg");
		dishRepository.save(dish);
		dish = new Dish();
		dish.setCategory(Category.MAIN_COURSE);
		dish.setDescription("Beef CheeseBurger with veggies and sauce");
		dish.setName("CheeseBurger");
		dish.setPrice(17);
		dish.setPrimaryImage("https://i.ibb.co/ft6XrV7/cheeseburger2.jpg");
		dish.setSecondaryImage("https://i.ibb.co/ft6XrV7/cheeseburger2.jpg");
		dishRepository.save(dish);
		dish = new Dish();
		dish.setCategory(Category.STARTER);
		dish.setDescription("Vegetable salad with olive oil");
		dish.setName("salad");
		dish.setPrice(15);
		dish.setPrimaryImage("https://i.ibb.co/Fz0BZzC/salad2.jpg");
		dish.setSecondaryImage("https://i.ibb.co/Fz0BZzC/salad2.jpg");
		dishRepository.save(dish);
		dish = new Dish();
		dish.setCategory(Category.STARTER);
		dish.setDescription("nachos sprinkled with cream cheese an good stuff");
		dish.setName("nachos");
		dish.setPrice(20);
		dish.setPrimaryImage("https://i.ibb.co/16gNqtZ/nachos2.jpg");
		dish.setSecondaryImage("https://i.ibb.co/16gNqtZ/nachos2.jpg");
		dishRepository.save(dish);
		dish = new Dish();
		dish.setCategory(Category.DESSERT);
		dish.setDescription("Tirmisu coffe cake");
		dish.setName("Tirmisu");
		dish.setPrice(10);
		dish.setPrimaryImage("https://i.ibb.co/qYYjNvk/tiramisu2.jpg");
		dish.setSecondaryImage("https://i.ibb.co/qYYjNvk/tiramisu2.jpg");
		dishRepository.save(dish);
		dish = new Dish();
		dish.setCategory(Category.SOUP);
		dish.setDescription("Chicken soup with potatoes");
		dish.setName("Chicken soup");
		dish.setPrice(10);
		dish.setPrimaryImage("https://i.ibb.co/T8ddVfF/chickensoup2.jpg");
		dish.setSecondaryImage("https://i.ibb.co/T8ddVfF/chickensoup2.jpg");
		dishRepository.save(dish);
		dish = new Dish();
		dish.setCategory(Category.SIDE_DISH);
		dish.setDescription("Bread slices");
		dish.setName("Bread");
		dish.setPrice(5);
		dish.setPrimaryImage("https://i.ibb.co/BTY6gs6/bread2.jpg");
		dish.setSecondaryImage("https://i.ibb.co/BTY6gs6/bread2.jpg");
		dishRepository.save(dish);
		User user = null;
		
		for (int i = 1; i <=  numberOfTables;i++) {
			user = new User();
			user.setName("table"+i);
	    	user.setLevel(Level.TABLE);
	    	user.setEmail(i+"@"+i+".com");
	    	user.setSalt(PasswordUtils.getSalt(30));
	    	String pass = Integer.toString(i)+Integer.toString(i)+Integer.toString(i);
	    	user.setPassword(PasswordUtils.generateSecurePassword(pass, user.getSalt()));
	    	branch1.addUser(user);

		}
		for (int i = 1; i <=  numberOfTables;i++) {
			user = new User();
			user.setName("table2"+i);
			user.setLevel(Level.TABLE);
			user.setEmail(i+"2@"+i+".com");
			user.setSalt(PasswordUtils.getSalt(30));
			String pass = Integer.toString(i)+Integer.toString(i)+Integer.toString(i);
			user.setPassword(PasswordUtils.generateSecurePassword(pass, user.getSalt()));
			branch2.addUser(user);
			
		}

    	user = new User();
    	user.setName("kitchen1");
    	user.setLevel(Level.KITCHEN);
    	user.setEmail("kitchen1@1.com");
    	user.setSalt(PasswordUtils.getSalt(30));
    	user.setPassword(PasswordUtils.generateSecurePassword("111111", user.getSalt()));
    	branch1.addUser(user);
    	user = new User();
    	user.setName("kitchen2");
    	user.setLevel(Level.KITCHEN);
    	user.setEmail("kitchen2@2.com");
    	user.setSalt(PasswordUtils.getSalt(30));
    	user.setPassword(PasswordUtils.generateSecurePassword("111111", user.getSalt()));
    	branch2.addUser(user);
    	user = new User();
    	user.setName("admin");
    	user.setLevel(Level.ADMIN);
    	user.setEmail("admin@admin.com");
    	user.setSalt(PasswordUtils.getSalt(30));
    	user.setPassword(PasswordUtils.generateSecurePassword("111111", user.getSalt()));
    	userRepository.save(user);		
    	user = new User();
    	user.setName("service1");
    	user.setLevel(Level.SERVICE);
    	user.setEmail("service1@1.com");
    	user.setSalt(PasswordUtils.getSalt(30));
    	user.setPassword(PasswordUtils.generateSecurePassword("111111", user.getSalt()));
    	branch1.addUser(user);
    	user = new User();
    	user.setName("service2");
    	user.setLevel(Level.SERVICE);
    	user.setEmail("service2@2.com");
    	user.setSalt(PasswordUtils.getSalt(30));
    	user.setPassword(PasswordUtils.generateSecurePassword("111111", user.getSalt()));
    	branch1.addUser(user);
		
	}

}
