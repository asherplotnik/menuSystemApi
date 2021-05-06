package app.core.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Dish;

public interface DishRepository extends JpaRepository<Dish, Integer>{
	
	List<Dish> findByAvailableIsFalse();
	
}
