package co.grandcircus.lab27.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.grandcircus.lab27.entity.Movies;

public interface MoviesDao extends JpaRepository<Movies, Long> {

	List<Movies> findByTitleContainsIgnoreCase(String title);

	List<Movies> findByCategoryContainsIgnoreCase(String category);

	
}
