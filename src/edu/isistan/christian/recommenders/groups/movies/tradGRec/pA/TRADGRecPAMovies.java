package edu.isistan.christian.recommenders.groups.movies.tradGRec.pA;

import java.util.ArrayList;
import java.util.List;

import edu.isistan.christian.recommenders.groups.tradGRec.pA.TRADGRecPA;
import edu.isistan.christian.recommenders.groups.tradGRec.pA.TRADGRecPAConfigs;
import edu.isistan.christian.recommenders.sur.movies.datatypes.MovieItem;

public abstract class TRADGRecPAMovies extends TRADGRecPA<MovieItem> {

	public TRADGRecPAMovies(TRADGRecPAConfigs<MovieItem> configs) {
		super(configs);
	}

	@Override
	public String toString() {		
		return "TRADGRecPA [singleUserRecommender="
		+ singleUserRecommender.toString()+ ", ratingsAggregationStrategy="
		+ aggregationStrategy.getClass().getSimpleName()+"]";
	}

	/**
	 * @param title
	 * @return a list with the movies with title matching {@literal 'title'}. Returns an empty list if there is no movie with given title
	 */
	public List<MovieItem> getMoviesByTitle(String title){
		List<MovieItem> movies = singleUserRecommender.getAllItems();
		List<MovieItem> result = new ArrayList<>();
		
		for (int i=0; i<movies.size();i++){
			if (movies.get(i).getTitle().equals(title)) 
				result.add(movies.get(i));
		}
		return result;
	}
	
}
