package app.core.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import app.core.enums.Level;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String address;
	private String phone;
	private Level level;
	private int affiliation;
	private String salt;
	@Column(unique = true)
	private String email;
	private String password;
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<MenuOrder> orders;
	public User() {
	}
	
	public User(int id) {
		this.id = id;
	}
	
	public User(String name, String address, String phone, Level level, String email, String password) {
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.level = level;
		this.email = email;
		this.password = password;
	}

	public User(Integer id, String name, String address, String phone, Level level, String email,
			String password) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.level = level;
		this.email = email;
		this.password = password;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<MenuOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<MenuOrder> orders) {
		this.orders = orders;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	
	public int getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(int id) {
		this.affiliation = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", address=" + address + ", phone=" + phone + ", level=" + level
				+ ", affiliation=" + affiliation + ", email=" + email + "]";
	}

	
	
	
}
