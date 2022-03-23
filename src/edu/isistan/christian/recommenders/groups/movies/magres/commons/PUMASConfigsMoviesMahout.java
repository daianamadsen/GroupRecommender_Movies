package edu.isistan.christian.recommenders.groups.movies.magres.commons;

import org.apache.commons.configuration.ConfigurationException;

import edu.isistan.christian.recommenders.groups.commons.pumas.PUMASConfigs;
import edu.isistan.christian.recommenders.sur.movies.datatypes.MovieItem;
import edu.isistan.christian.recommenders.sur.movies.mahout.MahoutMoviesSUR;
import edu.isistan.christian.recommenders.sur.movies.mahout.MahoutMoviesSURConfigs;

public class PUMASConfigsMoviesMahout extends PUMASConfigs<MovieItem>{
	
	MahoutMoviesSURConfigs mahoutConfigs;

	public PUMASConfigsMoviesMahout(String configsPath) throws ConfigurationException {
		super(configsPath);
	}
	
	@Override
	protected MahoutMoviesSUR buildSUR(String configsPath) throws ConfigurationException {
		mahoutConfigs = new MahoutMoviesSURConfigs(configsPath);
		
		//Build Movies SUR		
		return new MahoutMoviesSUR(mahoutConfigs.getMahoutRec(), mahoutConfigs.getDSLoader(), mahoutConfigs.getDSLoaderConfig(),
				mahoutConfigs.getMahoutRecConfig());
	}
	
	public MahoutMoviesSUR getSUR(){
		return (MahoutMoviesSUR) this.singleUserRecommender;
	}

}
