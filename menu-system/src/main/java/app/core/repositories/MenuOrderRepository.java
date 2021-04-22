package app.core.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import app.core.entities.MenuOrder;
import app.core.enums.Status;
import app.core.exceptions.MenuException;

public interface MenuOrderRepository extends JpaRepository<MenuOrder, Integer>{
	
	List<MenuOrder> findByStatus(Status status) throws MenuException;
	
}
