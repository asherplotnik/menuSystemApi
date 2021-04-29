package app.core.util;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;


public class DishPayload implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String description;
	private String category;
	private String price;
	private String available;
	private MultipartFile image1;
	private MultipartFile image2;
	
	
	
	
	public int getId() {
		return id;
	}




	public void setId(int id) {
		this.id = id;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public String getDescription() {
		return description;
	}




	public void setDescription(String description) {
		this.description = description;
	}




	public String getCategory() {
		return category;
	}




	public void setCategory(String category) {
		this.category = category;
	}




	public String getPrice() {
		return price;
	}




	public void setPrice(String price) {
		this.price = price;
	}




	public String getAvailable() {
		return available;
	}




	public void setAvailable(String available) {
		this.available = available;
	}




	public MultipartFile getImage1() {
		return image1;
	}




	public void setImage1(MultipartFile image1) {
		this.image1 = image1;
	}




	public MultipartFile getImage2() {
		return image2;
	}




	public void setImage2(MultipartFile image2) {
		this.image2 = image2;
	}

	
	
}
