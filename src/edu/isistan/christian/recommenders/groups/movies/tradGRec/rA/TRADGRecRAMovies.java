package edu.isistan.christian.recommenders.groups.movies.tradGRec.rA;

import java.util.ArrayList;
import java.util.List;

import edu.isistan.christian.recommenders.groups.tradGRec.rA.TRADGRecRA;
import edu.isistan.christian.recommenders.groups.tradGRec.rA.TRADGRecRAConfigs;
import edu.isistan.christian.recommenders.sur.movies.datatypes.MovieItem;

public abstract class TRADGRecRAMovies extends TRADGRecRA<MovieItem> {

	public TRADGRecRAMovies(TRADGRecRAConfigs<MovieItem> configs) {
		super(configs);
	}
	
	@Override
	public String toString() {
		return "TRADGRecRAMovies [singleUserRecommender=" + singleUserRecommender + ", recAggregationStrategy="
				+ recAggregationStrategy + ", groupRatingEstimationStrategy=" + groupRatingEstimationStrategy + "]";
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
