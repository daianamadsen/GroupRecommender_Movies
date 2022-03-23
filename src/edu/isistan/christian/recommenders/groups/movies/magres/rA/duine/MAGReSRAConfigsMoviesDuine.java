package edu.isistan.christian.recommenders.groups.movies.magres.rA.duine;

import org.apache.commons.configuration.ConfigurationException;

import edu.isistan.christian.recommenders.groups.magres.rA.MAGReSRAConfigs;
import edu.isistan.christian.recommenders.groups.movies.magres.commons.PUMASConfigsMoviesDuine;
import edu.isistan.christian.recommenders.sur.movies.datatypes.MovieItem;

public class MAGReSRAConfigsMoviesDuine extends MAGReSRAConfigs<MovieItem> {
		
	public MAGReSRAConfigsMoviesDuine(String configsPath)
			throws ConfigurationException {
		super(configsPath, new PUMASConfigsMoviesDuine(configsPath));
	}
	
}
