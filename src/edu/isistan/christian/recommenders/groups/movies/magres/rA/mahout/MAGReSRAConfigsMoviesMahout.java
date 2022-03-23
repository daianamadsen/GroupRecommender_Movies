package edu.isistan.christian.recommenders.groups.movies.magres.rA.mahout;

import org.apache.commons.configuration.ConfigurationException;

import edu.isistan.christian.recommenders.groups.magres.rA.MAGReSRAConfigs;
import edu.isistan.christian.recommenders.groups.movies.magres.commons.PUMASConfigsMoviesMahout;
import edu.isistan.christian.recommenders.sur.movies.datatypes.MovieItem;

public class MAGReSRAConfigsMoviesMahout extends MAGReSRAConfigs<MovieItem>{
		
	public MAGReSRAConfigsMoviesMahout(String configsPath)
			throws ConfigurationException {
		super(configsPath, new PUMASConfigsMoviesMahout(configsPath));
	}
	
}
