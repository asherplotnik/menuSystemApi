package app.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.MenuEntry;

public interface MenuEntryRepository extends JpaRepository<MenuEntry, Integer>{

}
