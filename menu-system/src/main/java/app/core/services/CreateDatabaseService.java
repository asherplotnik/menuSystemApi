package app.core.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import app.core.entities.Customer;
import app.core.entities.Dish;
import app.core.enums.Category;
import app.core.enums.Level;
import app.core.repositories.CustomerRepository;
import app.core.repositories.DishRepository;
import app.core.security.PasswordUtils;

@Service
@Transactional
public class CreateDatabaseService {
	@Autowired
	DishRepository dishRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Value("${number.of.tables:10}")
	int numberOfTables;
	
	public void createDatabase() {
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
		Customer customer = null;
		
		for (int i = 1; i <=  numberOfTables;i++) {
			customer = new Customer();
			customer.setName("table"+i);
	    	customer.setLevel(Level.CUSTOMER);
	    	customer.setEmail(i+"@"+i+".com");
	    	customer.setSalt(PasswordUtils.getSalt(30));
	    	String pass = Integer.toString(i)+Integer.toString(i)+Integer.toString(i);
	    	customer.setPassword(PasswordUtils.generateSecurePassword(pass, customer.getSalt()));
	    	customerRepository.save(customer);

		}
//		customer.setName("table1");
//    	customer.setLevel(Level.CUSTOMER);
//    	customer.setEmail("1@1.com");
//    	customer.setSalt(PasswordUtils.getSalt(30));
//    	customer.setPassword(PasswordUtils.generateSecurePassword("111", customer.getSalt()));
//    	customerRepository.save(customer);
//    	customer = new Customer();
//    	customer.setName("table2");
//    	customer.setLevel(Level.CUSTOMER);
//    	customer.setEmail("2@2.com");
//    	customer.setSalt(PasswordUtils.getSalt(30));
//    	customer.setPassword(PasswordUtils.generateSecurePassword("222", customer.getSalt()));
//    	customerRepository.save(customer);
//    	customer = new Customer();
//    	customer = new Customer();
//    	customer.setName("table3");
//    	customer.setLevel(Level.CUSTOMER);
//    	customer.setEmail("3@3.com");
//    	customer.setSalt(PasswordUtils.getSalt(30));
//    	customer.setPassword(PasswordUtils.generateSecurePassword("333", customer.getSalt()));
//    	customerRepository.save(customer);
//    	customer = new Customer();
//    	customer.setName("table4");
//    	customer.setLevel(Level.CUSTOMER);
//    	customer.setEmail("4@4.com");
//    	customer.setSalt(PasswordUtils.getSalt(30));
//    	customer.setPassword(PasswordUtils.generateSecurePassword("444", customer.getSalt()));
//    	customerRepository.save(customer);
//    	customer = new Customer();
//    	customer.setName("table5");
//    	customer.setLevel(Level.CUSTOMER);
//    	customer.setEmail("5@5.com");
//    	customer.setSalt(PasswordUtils.getSalt(30));
//    	customer.setPassword(PasswordUtils.generateSecurePassword("555", customer.getSalt()));
//    	customerRepository.save(customer);
//    	customer = new Customer();
//    	customer = new Customer();
//    	customer.setName("table6");
//    	customer.setLevel(Level.CUSTOMER);
//    	customer.setEmail("6@6.com");
//    	customer.setSalt(PasswordUtils.getSalt(30));
//    	customer.setPassword(PasswordUtils.generateSecurePassword("666", customer.getSalt()));
//    	customerRepository.save(customer);
//    	customer = new Customer();
//    	customer.setName("table7");
//    	customer.setLevel(Level.CUSTOMER);
//    	customer.setEmail("7@7.com");
//    	customer.setSalt(PasswordUtils.getSalt(30));
//    	customer.setPassword(PasswordUtils.generateSecurePassword("777", customer.getSalt()));
//    	customerRepository.save(customer);
//    	customer = new Customer();
//    	customer.setName("table8");
//    	customer.setLevel(Level.CUSTOMER);
//    	customer.setEmail("8@8.com");
//    	customer.setSalt(PasswordUtils.getSalt(30));
//    	customer.setPassword(PasswordUtils.generateSecurePassword("888", customer.getSalt()));
//    	customerRepository.save(customer);
//    	customer = new Customer();
//    	customer.setName("table9");
//    	customer.setLevel(Level.CUSTOMER);
//    	customer.setEmail("9@9.com");
//    	customer.setSalt(PasswordUtils.getSalt(30));
//    	customer.setPassword(PasswordUtils.generateSecurePassword("999", customer.getSalt()));
//    	customerRepository.save(customer);
//    	customer = new Customer();
//    	customer.setName("table10");
//    	customer.setLevel(Level.CUSTOMER);
//    	customer.setEmail("10@10.com");
//    	customer.setSalt(PasswordUtils.getSalt(30));
//    	customer.setPassword(PasswordUtils.generateSecurePassword("101010", customer.getSalt()));
//    	customerRepository.save(customer);		
    	customer = new Customer();
    	customer.setName("kitchen");
    	customer.setLevel(Level.KITCHEN);
    	customer.setEmail("11@11.com");
    	customer.setSalt(PasswordUtils.getSalt(30));
    	customer.setPassword(PasswordUtils.generateSecurePassword("111111", customer.getSalt()));
    	customerRepository.save(customer);		
    	customer = new Customer();
    	customer.setName("admin");
    	customer.setLevel(Level.ADMIN);
    	customer.setEmail("12@12.com");
    	customer.setSalt(PasswordUtils.getSalt(30));
    	customer.setPassword(PasswordUtils.generateSecurePassword("111111", customer.getSalt()));
    	customerRepository.save(customer);		
		
	}

}
