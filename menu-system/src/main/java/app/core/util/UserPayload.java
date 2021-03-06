package app.core.util;

import app.core.entities.Branch;
import app.core.enums.Level;

public class UserPayload {
	private Integer id;
	private String name;
	private String address;
	private String phone;
	private Level level;
	private Branch branch;
	private String email;
	private String token;
		
	public UserPayload() {
	}

	
	
	public UserPayload(Integer id, String name, String address, String phone, Level level,Branch branch, String email, String token) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.level = level;
		this.email = email;
		this.token = token;
		this.branch = branch;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}



	public Branch getBranch() {
		return branch;
	}



	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	
}
