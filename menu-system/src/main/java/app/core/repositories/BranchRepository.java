package app.core.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Branch;

public interface BranchRepository extends JpaRepository<Branch, Integer>{

	Optional<Branch> findById(int id);
	
}
