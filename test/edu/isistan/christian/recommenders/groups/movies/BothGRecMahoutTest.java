package edu.isistan.christian.recommenders.groups.movies;

import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.isistan.christian.recommenders.groups.commons.datatypes.GRecGroup;
import edu.isistan.christian.recommenders.groups.commons.datatypes.GRecRecommendation;
import edu.isistan.christian.recommenders.groups.commons.datatypes.GRecResult;
import edu.isistan.christian.recommenders.groups.movies.magres.rA.MAGReSRAMovies;
import edu.isistan.christian.recommenders.groups.movies.magres.rA.mahout.MAGReSRAConfigsMoviesMahout;
import edu.isistan.christian.recommenders.groups.movies.magres.rA.mahout.MAGReSRAMoviesMahout;
import edu.isistan.christian.recommenders.groups.movies.magres.rA.utils.MAGReSRAMoviesCSVExporter;
import edu.isistan.christian.recommenders.groups.movies.tradGRec.pA.TRADGRecPAMovies;
import edu.isistan.christian.recommenders.groups.movies.tradGRec.pA.mahout.TRADGRecPAConfigsMoviesMahout;
import edu.isistan.christian.recommenders.groups.movies.tradGRec.pA.mahout.TRADGRecPAMoviesMahout;
import edu.isistan.christian.recommenders.groups.movies.utils.MoviesCSVResultsExporter;
import edu.isistan.christian.recommenders.sur.exceptions.SURException;
import edu.isistan.christian.recommenders.sur.exceptions.SURInexistentUserException;
import edu.isistan.christian.recommenders.sur.movies.datatypes.MovieItem;

public class BothGRecMahoutTest {
	private static final Logger logger = LogManager.getLogger(BothGRecMahoutTest.class);
	
	private static final String CONFIGS_PATH_PUMAS = "edu/isistan/christian/recommenders/groups/movies/configs/PUMASGRecMahoutConfigs.properties";
	private static final String CONFIGS_PATH_TRAD = "edu/isistan/christian/recommenders/groups/movies/configs/TRADGRecMahoutConfigs.properties";
	
	private static final String RESULTS_EXPORT_PATH = "C:\\Users\\Christian\\Desktop";
	
	private static final boolean FORCE_REINITIALIZE = false;
	
	private static final int ITEMS_PER_REC = 10;
	
	public static void main (String[] args){
		
		try {
			
			//PUMAS
			MAGReSRAConfigsMoviesMahout mahoutPUMASConfigs = new MAGReSRAConfigsMoviesMahout(CONFIGS_PATH_PUMAS);
			MAGReSRAMovies pumasGRecMahout = new MAGReSRAMoviesMahout(mahoutPUMASConfigs);
			pumasGRecMahout.initialize(FORCE_REINITIALIZE);
			
			//TRADGRec
			TRADGRecPAConfigsMoviesMahout mahoutTRADGRecConfigs = new TRADGRecPAConfigsMoviesMahout(CONFIGS_PATH_TRAD);
			TRADGRecPAMovies tradGRecMahout = new TRADGRecPAMoviesMahout(mahoutTRADGRecConfigs);
			tradGRecMahout.initialize(FORCE_REINITIALIZE);

			GRecResult<MovieItem> resultPUMAS;
			GRecResult<MovieItem> resultGrec;

			try {
				
				GRecGroup group = new GRecGroup();
				group.add(pumasGRecMahout.getUser("94"));
				group.add(pumasGRecMahout.getUser("331"));
				group.add(pumasGRecMahout.getUser("682"));
				
				resultPUMAS = pumasGRecMahout.recommend(group, ITEMS_PER_REC);
				

				logger.info("-----------------------------------------------------------------------------------------");
				logger.info("PUMASGRec Recommendations for "+group+" ["+resultPUMAS.getRecommendations().size()+" of "+ITEMS_PER_REC+"]");
				logger.info("-----------------------------------------------------------------------------------------");
				for (GRecRecommendation<MovieItem> r : resultPUMAS.getRecommendations()){
					logger.info(r.toString());	
					logger.info(" >> Stats: "+ resultPUMAS.getRecommendationStats(r));
				}
				logger.info("-----------------------------------------------------------------------------------------");
				
				MoviesCSVResultsExporter exporterPUMAS = new MAGReSRAMoviesCSVExporter();
				exporterPUMAS.export(resultPUMAS, RESULTS_EXPORT_PATH, true);
				
				
				resultGrec = tradGRecMahout.recommend(group, ITEMS_PER_REC);
				logger.info("-----------------------------------------------------------------------------------------");
				logger.info("TRADGRec Recommendations for "+group+" ["+resultGrec.getRecommendations().size()+" of "+ITEMS_PER_REC+"]");
				logger.info("-----------------------------------------------------------------------------------------");
				for (GRecRecommendation<MovieItem> r : resultGrec.getRecommendations()){
					logger.info(r.toString());	
					logger.info(" >> Stats: "+ resultGrec.getRecommendationStats(r));
				}
				logger.info("-----------------------------------------------------------------------------------------");
				MoviesCSVResultsExporter exporterTRADGRec = new MoviesCSVResultsExporter();
				exporterTRADGRec.export(resultGrec, RESULTS_EXPORT_PATH, true);
				
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
