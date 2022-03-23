package edu.isistan.christian.recommenders.groups.movies.tradGRec.rA.mahout;

import java.util.List;

import org.apache.commons.configuration.ConfigurationException;

import edu.isistan.christian.recommenders.groups.tradGRec.rA.TRADGRecRAConfigs;
import edu.isistan.christian.recommenders.sur.movies.datatypes.MovieItem;
import edu.isistan.christian.recommenders.sur.movies.mahout.MahoutMoviesSUR;
import edu.isistan.christian.recommenders.sur.movies.mahout.MahoutMoviesSURConfigs;

public class TRADGRecRAConfigsMoviesMahout extends TRADGRecRAConfigs<MovieItem>{
	
	MahoutMoviesSURConfigs mahoutConfigs;
	
	public TRADGRecRAConfigsMoviesMahout(String configsPath)
			throws ConfigurationException {
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
	
	@Override
	public List<String> getRequiredProperties() {
		List<String> rProp = super.getRequiredProperties();
		rProp.addAll(mahoutConfigs.getRequiredProperties());
		
		return rProp;
	}
}
