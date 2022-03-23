package edu.isistan.christian.recommenders.groups.movies.magres.magresPA;

import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.isistan.christian.recommenders.groups.commons.datatypes.GRecGroup;
import edu.isistan.christian.recommenders.groups.commons.datatypes.GRecRecommendation;
import edu.isistan.christian.recommenders.groups.commons.datatypes.GRecResult;
import edu.isistan.christian.recommenders.groups.movies.magres.pA.MAGReSPAMovies;
import edu.isistan.christian.recommenders.groups.movies.magres.pA.duine.MAGReSPAConfigsMoviesDuine;
import edu.isistan.christian.recommenders.groups.movies.magres.pA.duine.MAGReSPAMoviesDuine;
import edu.isistan.christian.recommenders.groups.movies.utils.MoviesCSVResultsExporter;
import edu.isistan.christian.recommenders.sur.exceptions.SURException;
import edu.isistan.christian.recommenders.sur.exceptions.SURInexistentUserException;
import edu.isistan.christian.recommenders.sur.movies.datatypes.MovieItem;

public class MAGReSPADuineTest {
	private static final Logger logger = LogManager.getLogger(MAGReSPADuineTest.class);
	
	private static final String CONFIGS_PATH = "edu/isistan/christian/recommenders/groups/movies/configs/MAGReSPA_MahoutConfigs.properties";
	
	private static final boolean FORCE_REINITIALIZE = false;
	
	private static final int ITEMS_PER_REC = 10;
	
	private static void makeRecommendation (MAGReSPAMovies recommender, GRecGroup group, MoviesCSVResultsExporter exporter) throws SURException{
		try {
			
			GRecResult<MovieItem> result = recommender.recommend(group, ITEMS_PER_REC);

			logger.info("-----------------------------------------------------------------------------------------");
			logger.info("Recommendations for "+group+" ["+result.getRecommendations().size()+" of "+ITEMS_PER_REC+"]");
			logger.info("-----------------------------------------------------------------------------------------");
			for (GRecRecommendation<MovieItem> r : result.getRecommendations()){
				logger.info(r.toString());	
				logger.info(" >> Stats: "+ result.getRecommendationStats(r));
			}
			logger.info("-----------------------------------------------------------------------------------------");
			
			
			exporter.export(result, "C:\\Users\\Christian\\Desktop", true);
			
		} catch (SURInexistentUserException | IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void main (String[] args){
		
		try {
			
			MAGReSPAConfigsMoviesDuine duineConfigs = new MAGReSPAConfigsMoviesDuine(CONFIGS_PATH);
			
			MAGReSPAMovies pumasGRecDuine = new MAGReSPAMoviesDuine(duineConfigs);
			
			pumasGRecDuine.initialize(FORCE_REINITIALIZE);

			MoviesCSVResultsExporter exporter = new MoviesCSVResultsExporter();
			
			//Start test
			
			GRecGroup group = new GRecGroup();
			
			group.add(pumasGRecDuine.getUser("579"));
			group.add(pumasGRecDuine.getUser("612"));
			group.add(pumasGRecDuine.getUser("94"));
			
//			group.add(pumasGRecDuine.getUser("236"));
//			group.add(pumasGRecDuine.getUser("579"));
//			group.add(pumasGRecDuine.getUser("612"));
//			group.add(pumasGRecDuine.getUser("94"));
						
			
			makeRecommendation(pumasGRecDuine, group, exporter);

		} catch (SURException | ConfigurationException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

	}
}
