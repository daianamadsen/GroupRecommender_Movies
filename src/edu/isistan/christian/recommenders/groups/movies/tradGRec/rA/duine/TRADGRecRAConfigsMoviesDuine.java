package edu.isistan.christian.recommenders.groups.movies.tradGRec.rA.duine;

import java.util.List;

import org.apache.commons.configuration.ConfigurationException;

import edu.isistan.christian.recommenders.groups.tradGRec.rA.TRADGRecRAConfigs;
import edu.isistan.christian.recommenders.sur.movies.datatypes.MovieItem;
import edu.isistan.christian.recommenders.sur.movies.duine.DuineMoviesSUR;
import edu.isistan.christian.recommenders.sur.movies.duine.DuineMoviesSURConfigs;
import edu.isistan.datasetLoader.MoviesDSLoader;

public class TRADGRecRAConfigsMoviesDuine extends TRADGRecRAConfigs<MovieItem> {
	
	DuineMoviesSURConfigs duineConfigs;
	
	public TRADGRecRAConfigsMoviesDuine(String configsPath)
			throws ConfigurationException {
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
	
	public MoviesDSLoader getDSLoader(){
		if (duineConfigs == null) //it should never happen => because if the configs are null then the buildSUR threw an exception and the constructor threw an exception
			return null;
		return duineConfigs.getDSLoader(); 
	}
	
	@Override
	public List<String> getRequiredProperties() {
		List<String> rProp = super.getRequiredProperties();
		rProp.addAll(duineConfigs.getRequiredProperties());
		
		return rProp;
	}
	
}
