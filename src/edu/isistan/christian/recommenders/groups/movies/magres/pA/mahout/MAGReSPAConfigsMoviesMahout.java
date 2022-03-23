package edu.isistan.christian.recommenders.groups.movies.magres.pA.mahout;

import org.apache.commons.configuration.ConfigurationException;

import edu.isistan.christian.recommenders.groups.magres.pA.MAGReSPAConfigs;
import edu.isistan.christian.recommenders.groups.movies.magres.commons.PUMASConfigsMoviesMahout;
import edu.isistan.christian.recommenders.sur.movies.datatypes.MovieItem;

public class MAGReSPAConfigsMoviesMahout extends MAGReSPAConfigs<MovieItem> {
		
	public MAGReSPAConfigsMoviesMahout(String configsPath)
			throws ConfigurationException {
		super(configsPath, new PUMASConfigsMoviesMahout(configsPath));
	}
	
}
