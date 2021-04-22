package app.core.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import app.core.entities.Customer;
import app.core.entities.Dish;
import app.core.entities.MenuEntry;
import app.core.entities.MenuOrder;
import app.core.enums.OrderType;
import app.core.enums.Status;
import app.core.exceptions.MenuException;
import app.core.repositories.CustomerRepository;
import app.core.repositories.DishRepository;
import app.core.repositories.MenuEntryRepository;
import app.core.repositories.MenuOrderRepository;
import app.core.security.JwtUtil;
import app.core.util.EntryObj;
import app.core.util.OrderPayload;

@Service
@Transactional
public class OrderService {
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	MenuOrderRepository menuOrderRepository;
	@Autowired
	MenuEntryRepository menuEntryRepository;
	@Autowired
	DishRepository dishRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Value("${number.of.tables:10}")
	int numberOfTables;
	

	public MenuOrder sendNewOrder(String token, OrderPayload orderPayload) throws MenuException {
		try {
			int customerId = jwtUtil.extractId(token);
			
			Optional<Customer> opt = customerRepository.findById(customerId);
			if (opt.isEmpty()) {
				throw new MenuException("Customer not found!!!");
			}
			Customer curr = opt.get();
			if (orderPayload.orderType == null) {
				throw new MenuException("invalid order type!!!");
			}
			if (orderPayload.orderType == OrderType.TABLE) {
				if (curr.getId() >= numberOfTables) {
					throw new MenuException("save order failed - invalid table number!!!");
				}
			} else {
				if (curr.getId() < numberOfTables) {
					throw new MenuException("save order failed - invalid customer id number!!!");
				}
			}
			if (orderPayload.entries == null || orderPayload.entries.isEmpty()) {
				throw new MenuException("save order failed - order is empty!!!");
			}
			MenuOrder newOrder = new  MenuOrder();
			newOrder.setCustomer(curr);
			newOrder.setNote(orderPayload.note);
			newOrder.setTime(orderPayload.time);
			newOrder.setOrderType(orderPayload.orderType);
			newOrder.setStatus(Status.ORDERED);
			newOrder = menuOrderRepository.save(newOrder);
			for (EntryObj entry : orderPayload.entries) {
				MenuEntry currEntry = new MenuEntry();
				Optional<Dish> optDish = dishRepository.findById(entry.dishId);
				if (optDish.isEmpty()) {
					throw new MenuException("save order failed - invalid dish");
				}
				currEntry.setDish(optDish.get());
				currEntry.setQuantity(entry.qty);
				currEntry = menuEntryRepository.save(currEntry);
				newOrder.addEntry(currEntry);
			}
    		return newOrder ;
		} catch (Exception e) {
			throw new MenuException("save order failed - " + e.getLocalizedMessage());
		}
	}

	public MenuOrder updateOrderStatus(int orderId, Status status) throws MenuException {
		try {
			Optional<MenuOrder> opt = menuOrderRepository.findById(orderId); 
			if (opt.isEmpty()) {
				throw new MenuException("update status order failed - Not found!!!");
			}
			boolean flag=true;
			for(MenuEntry entry : opt.get().getEntries()) {
				if(entry.getReady()==null) {
					flag=false;
					break;
				}
			}
			if(flag) {
				opt.get().setStatus(status);
				return opt.get();
			} else {
				throw new MenuException("update status order failed - ORDER NOT READY!!!");
			}
		} catch (Exception e) {
			throw new MenuException("update status order failed - "+ e.getLocalizedMessage());
		}
	}
		
	public List<Dish> getMenu() throws MenuException{
		try {
			return dishRepository.findAll();
		} catch (Exception e) {
			throw new MenuException("update status order failed - "+ e.getLocalizedMessage());
		}
	}
	
	public List<MenuOrder> getOrdersByStatus(Status status) throws MenuException {
		try {
			return menuOrderRepository.findByStatus(status);
		} catch (Exception e) {
			throw new MenuException(e.getLocalizedMessage());
		}
	}
 
	public LocalDateTime updateEntryStatus(int entryId) throws MenuException{
		try {
			Optional<MenuEntry> opt = menuEntryRepository.findById(entryId);
			if (opt.isEmpty()) {
				throw new MenuException("Update Entry failed !!!");
			}
			MenuEntry entry = opt.get();
			entry.setReady(entry.getReady() == null ? LocalDateTime.now(): null);
			return entry.getReady();
		} catch (Exception e) {
			throw new MenuException(e.getLocalizedMessage());
		}
		
	}
	
}
