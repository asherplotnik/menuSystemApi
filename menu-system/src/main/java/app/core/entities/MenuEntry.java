package app.core.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class MenuEntry {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	private Dish dish;
	private LocalDateTime ready ;
	private int quantity;
	@JsonIgnore
	@ManyToOne
	private MenuOrder menuOrder;
	
	public MenuEntry() {
	}

	public MenuEntry(Dish dish, LocalDateTime ready, MenuOrder menuOrder) {
		this.dish = dish;
		this.ready = ready;
		this.menuOrder = menuOrder;
	}

	public MenuEntry(Integer id, Dish dish, LocalDateTime ready, MenuOrder menuOrder) {
		this.id = id;
		this.dish = dish;
		this.ready = ready;
	}

	public Integer getId() {
		return id;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}

	public LocalDateTime getReady() {
		return ready;
	}

	public void setReady(LocalDateTime ready) {
		this.ready = ready;
	}

	public MenuOrder getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(MenuOrder menuOrder) {
		this.menuOrder = menuOrder;
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
		MenuEntry other = (MenuEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MenuEntry [id=" + id + ", dish=" + dish + ", ready=" + ready + ", quantity=" + quantity + ", menuOrderID="
				+ menuOrder.getId() + "]";
	}

	
}
