package edu.isistan.christian.recommenders.groups.movies.magres.magresRA;

import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.isistan.christian.recommenders.groups.commons.datatypes.GRecGroup;
import edu.isistan.christian.recommenders.groups.commons.datatypes.GRecRecommendation;
import edu.isistan.christian.recommenders.groups.commons.datatypes.GRecResult;
import edu.isistan.christian.recommenders.groups.movies.magres.rA.MAGReSRAMovies;
import edu.isistan.christian.recommenders.groups.movies.magres.rA.duine.MAGReSRAConfigsMoviesDuine;
import edu.isistan.christian.recommenders.groups.movies.magres.rA.duine.MAGReSRAMoviesDuine;
import edu.isistan.christian.recommenders.groups.movies.magres.rA.utils.MAGReSRAMoviesCSVExporter;
import edu.isistan.christian.recommenders.groups.movies.utils.MoviesCSVResultsExporter;
import edu.isistan.christian.recommenders.sur.exceptions.SURException;
import edu.isistan.christian.recommenders.sur.exceptions.SURInexistentUserException;
import edu.isistan.christian.recommenders.sur.movies.datatypes.MovieItem;

public class MAGReSRADuineTest {
	private static final Logger logger = LogManager.getLogger(MAGReSRADuineTest.class);
	
	private static final String CONFIGS_PATH = "edu/isistan/christian/recommenders/groups/movies/configs/MAGReSRA_DuineConfigs.properties";
	
	private static final boolean FORCE_REINITIALIZE = false;
	
	private static final int ITEMS_PER_REC = 10;
	
	public static void main (String[] args){
		
		try {
			
			MAGReSRAConfigsMoviesDuine duineConfigs = new MAGReSRAConfigsMoviesDuine(CONFIGS_PATH);
			
			MAGReSRAMovies pumasGRecDuine = new MAGReSRAMoviesDuine(duineConfigs);
			
			pumasGRecDuine.initialize(FORCE_REINITIALIZE);

			GRecResult<MovieItem> result;

			try {
				
				GRecGroup group = new GRecGroup();
//				group.add(pumasGRecDuine.getUser("792"));
//				group.add(pumasGRecDuine.getUser("726"));
//				group.add(pumasGRecDuine.getUser("448"));
				group.add(pumasGRecDuine.getUser("94"));
				group.add(pumasGRecDuine.getUser("331"));
				group.add(pumasGRecDuine.getUser("682"));
				
				result = pumasGRecDuine.recommend(group, ITEMS_PER_REC);

				logger.info("-----------------------------------------------------------------------------------------");
				logger.info("Recommendations for "+group+" ["+result.getRecommendations().size()+" of "+ITEMS_PER_REC+"]");
				logger.info("-----------------------------------------------------------------------------------------");
				for (GRecRecommendation<MovieItem> r : result.getRecommendations()){
					logger.info(r.toString());	
					logger.info(" >> Stats: "+ result.getRecommendationStats(r));
				}
				logger.info("-----------------------------------------------------------------------------------------");
				
				MoviesCSVResultsExporter exporter = new MAGReSRAMoviesCSVExporter();
				exporter.export(result, "C:\\Users\\Christian\\Desktop", true);
				
			} catch (SURInexistentUserException | IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}

		} catch (SURException | ConfigurationException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

	}
}
