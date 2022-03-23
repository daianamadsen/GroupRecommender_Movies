package edu.isistan.christian.recommenders.groups.movies.tradGRec.pA.mahout;

import java.util.List;

import org.apache.commons.configuration.ConfigurationException;

import edu.isistan.christian.recommenders.groups.tradGRec.pA.TRADGRecPAConfigs;
import edu.isistan.christian.recommenders.sur.movies.datatypes.MovieItem;
import edu.isistan.christian.recommenders.sur.movies.mahout.MahoutMoviesSUR;
import edu.isistan.christian.recommenders.sur.movies.mahout.MahoutMoviesSURConfigs;

public class TRADGRecPAConfigsMoviesMahout extends TRADGRecPAConfigs<MovieItem>{
	
	MahoutMoviesSURConfigs mahoutConfigs;
	
	public TRADGRecPAConfigsMoviesMahout(String configsPath)
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
