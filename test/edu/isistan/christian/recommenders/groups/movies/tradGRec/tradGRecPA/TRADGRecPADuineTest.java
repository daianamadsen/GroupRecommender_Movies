package edu.isistan.christian.recommenders.groups.movies.tradGRec.tradGRecPA;

import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.isistan.christian.recommenders.groups.commons.datatypes.GRecGroup;
import edu.isistan.christian.recommenders.groups.commons.datatypes.GRecRecommendation;
import edu.isistan.christian.recommenders.groups.commons.datatypes.GRecResult;
import edu.isistan.christian.recommenders.groups.movies.tradGRec.pA.TRADGRecPAMovies;
import edu.isistan.christian.recommenders.groups.movies.tradGRec.pA.duine.TRADGRecPAConfigsMoviesDuine;
import edu.isistan.christian.recommenders.groups.movies.tradGRec.pA.duine.TRADGRecPAMoviesDuine;
import edu.isistan.christian.recommenders.groups.movies.utils.MoviesCSVResultsExporter;
import edu.isistan.christian.recommenders.sur.exceptions.SURException;
import edu.isistan.christian.recommenders.sur.exceptions.SURInexistentUserException;
import edu.isistan.christian.recommenders.sur.movies.datatypes.MovieItem;

public class TRADGRecPADuineTest {
	private static final Logger logger = LogManager.getLogger(TRADGRecPADuineTest.class);
	
	private static final String CONFIGS_PATH = "edu/isistan/christian/recommenders/groups/movies/configs/TRADGRecRA_DuineConfigs.properties";
	
	private static final boolean FORCE_REINITIALIZE = false;
	
	private static final int ITEMS_PER_REC = 10;
	
	public static void main (String[] args){
		
		try {
			
			TRADGRecPAConfigsMoviesDuine duineConfigs = new TRADGRecPAConfigsMoviesDuine(CONFIGS_PATH);
			
			TRADGRecPAMovies duineGRec = new TRADGRecPAMoviesDuine (duineConfigs);
			
			duineGRec.initialize(FORCE_REINITIALIZE);

			GRecResult<MovieItem> result;

			try {
				
				GRecGroup group = new GRecGroup();
//				group.add(duineGRec.getUser("94"));
//				group.add(duineGRec.getUser("331"));
//				group.add(duineGRec.getUser("682"));
				group.add(duineGRec.getUser("579"));
				group.add(duineGRec.getUser("612"));
				group.add(duineGRec.getUser("94"));

				
				result = duineGRec.recommend(group, ITEMS_PER_REC);

				logger.info("-----------------------------------------------------------------------------------------");
				logger.info("Recommendations for "+group+" ["+result.getRecommendations().size()+" of "+ITEMS_PER_REC+"]");
				logger.info("-----------------------------------------------------------------------------------------");
				for (GRecRecommendation<MovieItem> r : result.getRecommendations()){
					logger.info(r.toString());	
					logger.info(" >> Stats: "+ result.getRecommendationStats(r));
				}
				logger.info("-----------------------------------------------------------------------------------------");
				
				MoviesCSVResultsExporter exporter = new MoviesCSVResultsExporter();
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