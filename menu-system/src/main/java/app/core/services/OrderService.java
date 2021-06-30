package app.core.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import app.core.entities.User;
import app.core.entities.Dish;
import app.core.entities.MenuEntry;
import app.core.entities.MenuOrder;
import app.core.enums.OrderType;
import app.core.enums.Status;
import app.core.exceptions.MenuException;
import app.core.repositories.UserRepository;
import app.core.repositories.BranchRepository;
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
	UserRepository userRepository;
	@Autowired
	BranchRepository branchRepository;
	@Value("${number.of.tables:10}")
	int numberOfTables;

	public MenuOrder sendNewOrder(String token, OrderPayload orderPayload) throws MenuException {
		try {
			int customerId = jwtUtil.extractId(token);
			Optional<User> opt = userRepository.findById(customerId);
			if (opt.isEmpty()) {
				throw new MenuException("Customer not found!!!");
			}
			User curr = opt.get();
			if (orderPayload.orderType == null) {
				throw new MenuException("invalid order type!!!");
			}
			if (orderPayload.orderType == OrderType.TABLE) {
				if (curr.getId() > numberOfTables + 2) {
					throw new MenuException("save order failed - invalid table number!!!");
				}
			} else {
				System.out.println(curr.getId());
				if (curr.getId() < numberOfTables && orderPayload.orderType == OrderType.DELIVERY) {
					throw new MenuException("save order failed - invalid customer id number!!!");
				}
			}
			if (orderPayload.entries == null || orderPayload.entries.isEmpty()) {
				throw new MenuException("save order failed - order is empty!!!");
			}
			MenuOrder newOrder = new MenuOrder();
			newOrder.setUser(curr);
			newOrder.setNote(orderPayload.note);
			newOrder.setTime(LocalDateTime.now());
			newOrder.setOrderType(orderPayload.orderType);
			newOrder.setStatus(Status.ORDERED);
			newOrder.setBranch(curr.getBranch());
			newOrder = menuOrderRepository.save(newOrder);
			for (EntryObj entry : orderPayload.entries) {
				MenuEntry currEntry = new MenuEntry();
				Optional<Dish> optDish = dishRepository.findById(entry.dishId);
				if (optDish.isEmpty()) {
					throw new MenuException("save order failed - invalid dish");
				}
				currEntry.setDish(optDish.get());
				currEntry.setQuantity(entry.quantity);
				currEntry = menuEntryRepository.save(currEntry);
				newOrder.addEntry(currEntry);
			}
			return newOrder;
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
			boolean flag = true;
			for (MenuEntry entry : opt.get().getEntries()) {
				if (entry.getReady() == null) {
					flag = false;
					break;
				}
			}
			if (flag) {
				opt.get().setStatus(status);
				switch (status) {
				case READY:
					opt.get().setTimeReady(LocalDateTime.now());
					break;
				case SERVED:
					opt.get().setTimeServed(LocalDateTime.now());
					break;
				case PAID:
				case CANCELED:
				case RETURNED:
					opt.get().setTimePaid(LocalDateTime.now());
					break;
				case ORDERED:
					opt.get().setTime(LocalDateTime.now());
					break;
				default:
				}
				return opt.get();
			} else {
				throw new MenuException("update status order failed - ORDER NOT READY!!!");
			}
		} catch (Exception e) {
			throw new MenuException("update status order failed - " + e.getLocalizedMessage());
		}
	}

	public List<Dish> getMenu() throws MenuException {
		try {
			return dishRepository.findAll();
		} catch (Exception e) {
			throw new MenuException("update status order failed - " + e.getLocalizedMessage());
		}
	}

	public Dish getDish(int id) throws MenuException {
		try {
			Optional<Dish> opt = dishRepository.findById(id);
			if (opt.isEmpty()) {
				throw new MenuException("failed to get Dish - not found");
			}
			return opt.get();
		} catch (Exception e) {
			throw new MenuException("failed to get Dish" + e.getLocalizedMessage());
		}
	}

	public List<MenuOrder> getOrdersByStatus(Status status, String token) throws MenuException {
		int customerId = jwtUtil.extractId(token);
		Optional<User> opt = userRepository.findById(customerId);
		if (opt.isEmpty()) {
			throw new MenuException("Customer not found!!!");
		}
		User curr = opt.get();
		if (!jwtUtil.extractUserType(token).equals("ADMIN")) {
			try {
				return menuOrderRepository.findByStatusAndBranch(status,curr.getBranch());
			} catch (Exception e) {
				throw new MenuException(e.getLocalizedMessage());
			}
		}else {
			try {
				return menuOrderRepository.findByStatus(status);
			} catch (Exception e) {
				throw new MenuException(e.getLocalizedMessage());
			}
		}
	}

	public LocalDateTime updateEntryStatus(int entryId) throws MenuException {
		try {
			Optional<MenuEntry> opt = menuEntryRepository.findById(entryId);
			if (opt.isEmpty()) {
				throw new MenuException("Update Entry failed !!!");
			}
			MenuEntry entry = opt.get();
			entry.setReady(entry.getReady() == null ? LocalDateTime.now() : null);
			return entry.getReady();
		} catch (Exception e) {
			throw new MenuException(e.getLocalizedMessage());
		}

	}

}
