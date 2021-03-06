package app.core.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Branch {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(unique=true)
	private String name;
	private String address;
	@OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
	private List<MenuOrder> orders;
	@JsonIgnore
	@OneToMany(mappedBy = "branch", cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REFRESH,CascadeType.MERGE})
	private List<User> users;
	public Branch() {
	}
	
	
	public Branch(String name, String address) {
		this.name = name;
		this.address = address;
	}

	public void addUser(User user) {
		if (users == null) {
			users = new ArrayList<>();
		}
		user.setBranch(this);
		users.add(user);
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
		
	public List<MenuOrder> getOrders() {
		return orders;
	}
	public void setOrders(List<MenuOrder> orders) {
		this.orders = orders;
	}
	
	
	
	public List<User> getUsers() {
		return users;
	}


	public void setUsers(List<User> users) {
		this.users = users;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Branch other = (Branch) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Branch [id=" + id + ", name=" + name + ", address=" + address + "]";
	}
	
	
}
