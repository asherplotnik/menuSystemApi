package app.core.entities;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import app.core.enums.Category;

@Entity
public class Dish {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(unique=true)
	private String name;
	@Column(length = 500)
	private String description;
	private double price;
	private boolean available = true;
	private String primaryImage;
	private String secondaryImage;
	private Category category;
	@JsonIgnore
	@OneToMany(mappedBy="dish",cascade = CascadeType.ALL)
	private List<MenuEntry> entries;
	
	
	public Dish() {
	}
	
	public Dish(int id) {
		this.id = id;
	}
	
	public Dish(String name, String description, double price, boolean available, String primaryImage, String secondaryImage,
			Category category) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.available = available;
		this.primaryImage = primaryImage;
		this.secondaryImage = secondaryImage;
		this.category = category;
	}

	public Dish(Integer id, String name, String description, double price,boolean available, String primaryImage, String secondaryImage,
			Category category) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.available = available;
		this.primaryImage = primaryImage;
		this.secondaryImage = secondaryImage;
		this.category = category;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getPrimaryImage() {
		return primaryImage;
	}

	public void setPrimaryImage(String primaryImage) {
		this.primaryImage = primaryImage;
	}

	public String getSecondaryImage() {
		return secondaryImage;
	}

	public void setSecondaryImage(String secondaryImage) {
		this.secondaryImage = secondaryImage;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<MenuEntry> getEntries() {
		return entries;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	
	public void setEntries(List<MenuEntry> entries) {
		this.entries = entries;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dish other = (Dish) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Dish [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", primaryImage=" + primaryImage + ", secondaryImage=" + secondaryImage + ", category=" + category
				+ "]";
	}
	
	

}
