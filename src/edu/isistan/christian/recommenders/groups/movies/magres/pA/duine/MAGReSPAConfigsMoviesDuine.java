package edu.isistan.christian.recommenders.groups.movies.magres.pA.duine;

import org.apache.commons.configuration.ConfigurationException;

import edu.isistan.christian.recommenders.groups.magres.pA.MAGReSPAConfigs;
import edu.isistan.christian.recommenders.groups.movies.magres.commons.PUMASConfigsMoviesDuine;
import edu.isistan.christian.recommenders.sur.movies.datatypes.MovieItem;

public class MAGReSPAConfigsMoviesDuine extends MAGReSPAConfigs<MovieItem> {
		
	public MAGReSPAConfigsMoviesDuine(String configsPath)
			throws ConfigurationException {
		super(configsPath, new PUMASConfigsMoviesDuine(configsPath));
	}
	
}
