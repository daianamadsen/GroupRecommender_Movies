package edu.isistan.christian.recommenders.groups.movies.magres.commons;

import org.apache.commons.configuration.ConfigurationException;

import edu.isistan.christian.recommenders.groups.commons.pumas.PUMASConfigs;
import edu.isistan.christian.recommenders.sur.movies.datatypes.MovieItem;
import edu.isistan.christian.recommenders.sur.movies.duine.DuineMoviesSUR;
import edu.isistan.christian.recommenders.sur.movies.duine.DuineMoviesSURConfigs;

public class PUMASConfigsMoviesDuine extends PUMASConfigs<MovieItem>{
	
	DuineMoviesSURConfigs duineConfigs;

	public PUMASConfigsMoviesDuine(String configsPath) throws ConfigurationException {
		super(configsPath);
	}
	
	@Override
	protected DuineMoviesSUR buildSUR(String configsPath) throws ConfigurationException {
		duineConfigs = new DuineMoviesSURConfigs(configsPath);
		
		//Build Movies SUR		
		return new DuineMoviesSUR(duineConfigs.getMovieLensRecommender(), duineConfigs.getDSLoader(), duineConfigs.getDSLoaderConfig(),
				duineConfigs.getPredRatingCriterion());
	}
	
	public DuineMoviesSUR getSUR(){
		return (DuineMoviesSUR) this.singleUserRecommender;
	}
	
//	public MoviesDSLoader getDSLoader(){
//		if (duineConfigs == null) //it should never happen => because if the configs are null then the buildSUR threw an exception and the constructor threw an exception
//			return null;
//		return duineConfigs.getDSLoader(); 
//	}

}
