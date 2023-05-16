package fr.wcs.sylene.yourtodolist.repository;

import fr.wcs.sylene.yourtodolist.model.Todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

}