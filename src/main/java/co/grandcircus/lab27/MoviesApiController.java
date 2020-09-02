package co.grandcircus.lab27;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.grandcircus.lab27.dao.MoviesDao;
import co.grandcircus.lab27.entity.Movies;

@RestController
public class MoviesApiController {
	
	@Autowired
	private MoviesDao dao;
	
	@GetMapping("/movies")
	public List<Movies> listMoviesByTitle(@RequestParam(required = false) String title, @RequestParam(required = false) String category) {
		if (title != null)  {
				return dao.findByTitleContainsIgnoreCase(title);
			} else if (category != null) {
				return dao.findByCategoryContainsIgnoreCase(category);
			} else {
				return dao.findAll();
			}
		}
	
	@PostMapping("/movies")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Movies createMovies(@RequestBody Movies newMovie) {
		return dao.save(newMovie);
	}
	
	@GetMapping("/movies/{id}")
	public  Optional<Movies> listMoviesById(@PathVariable("id") Long id) {
		return dao.findById(id);
	}
	
	@GetMapping("/random-movie")
	public Optional<Movies> showRandomMovie(@RequestParam(required = false) String category) {
		if (category != null) {
			List<Movies> categoryList = dao.findByCategoryContainsIgnoreCase(category);
			int randomInt = (int)(Math.random() * (categoryList.size()));
			return Optional.ofNullable(categoryList.get(randomInt));
		}
		
		int randomInt = (int)(Math.random() * (dao.findAll().size() + 1));
		Long randomId = (long) randomInt;
		return dao.findById(randomId);
	}
	
	@GetMapping("/random-movies")
	public List<Movies> showSomeRandomMovies(@RequestParam("quantity") Integer quantity) {
		List<Movies> quantityList = null;
		for (int i = 0; i < quantity; i++) {
			int randomInt = (int)(Math.random() * (dao.findAll().size() + 1));
			Long randomId = (long) randomInt;
			quantityList.add(dao.findAll().get(randomInt));
		}
	}
	
	@GetMapping("/categories")
	public HashSet<String> showCategories() {
		HashSet<String> categorySet = new HashSet<String>();
		for (int i = 1; i <= dao.count(); i++) {
		String firstCat = dao.getOne((long) i).getCategory();
		categorySet.add(firstCat);
		}
		return categorySet;
		
	}
}