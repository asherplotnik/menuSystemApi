package app.core.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import app.core.enums.OrderType;
import app.core.enums.Status;

@Entity
public class MenuOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private OrderType orderType;
	@ManyToOne
	private Customer customer;
	private LocalDateTime time;
	private String note;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "menuOrder")
	private List<MenuEntry> entries = new ArrayList<>();
	private Status status; 
	
	public MenuOrder() {
	}

	public MenuOrder(OrderType orderType, Customer customer, LocalDateTime time, String note) {
		this.orderType = orderType;
		this.customer = customer;
		this.time = time;
		this.note = note;
	}

	public MenuOrder(Integer id, OrderType orderType, Customer customer, LocalDateTime time, String note,
			Status status) {
		this.id = id;
		this.orderType = orderType;
		this.customer = customer;
		this.time = time;
		this.note = note;
		this.status = status;
	}
	
	

	public MenuOrder(OrderType orderType, Customer customer, LocalDateTime time, String note, List<MenuEntry> entries,
			Status status) {
		this.orderType = orderType;
		this.customer = customer;
		this.time = time;
		this.note = note;
		this.entries = entries;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<MenuEntry> getEntries() {
		return entries;
	}
	
	public void addEntry(MenuEntry entry) {
		entry.setMenuOrder(this);
		entries.add(entry);
	}

	public void setEntries(List<MenuEntry> entries) {
		this.entries = entries;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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
		MenuOrder other = (MenuOrder) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MenuOrder [id=" + id + ", orderType=" + orderType + ", customer=" + customer + ", time=" + time
				+ ", note=" + note + ", status=" + status + ", entries=" + entries + "]";
	}
	
}
