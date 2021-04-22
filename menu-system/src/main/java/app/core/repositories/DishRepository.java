package app.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Dish;

public interface DishRepository extends JpaRepository<Dish, Integer>{
	
}
