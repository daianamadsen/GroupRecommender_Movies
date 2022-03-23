package edu.isistan.christian.recommenders.groups.movies.magres.magresPA;

import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.isistan.christian.recommenders.groups.commons.datatypes.GRecGroup;
import edu.isistan.christian.recommenders.groups.commons.datatypes.GRecRecommendation;
import edu.isistan.christian.recommenders.groups.commons.datatypes.GRecResult;
import edu.isistan.christian.recommenders.groups.movies.magres.pA.MAGReSPAMovies;
import edu.isistan.christian.recommenders.groups.movies.magres.pA.mahout.MAGReSPAConfigsMoviesMahout;
import edu.isistan.christian.recommenders.groups.movies.magres.pA.mahout.MAGReSPAMoviesMahout;
import edu.isistan.christian.recommenders.groups.movies.utils.MoviesCSVResultsExporter;
import edu.isistan.christian.recommenders.sur.exceptions.SURException;
import edu.isistan.christian.recommenders.sur.exceptions.SURInexistentUserException;
import edu.isistan.christian.recommenders.sur.movies.datatypes.MovieItem;

public class MAGReSPAMahoutTest {
	private static final Logger logger = LogManager.getLogger(MAGReSPAMahoutTest.class);
	
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
			
			MAGReSPAConfigsMoviesMahout mahoutConfigs = new MAGReSPAConfigsMoviesMahout(CONFIGS_PATH);
			
			MAGReSPAMovies pumasGRecMahout = new MAGReSPAMoviesMahout(mahoutConfigs);
			
			pumasGRecMahout.initialize(FORCE_REINITIALIZE);

			MoviesCSVResultsExporter exporter = new MoviesCSVResultsExporter();
			
			//Start test
			
			GRecGroup group = new GRecGroup();
			
			group.add(pumasGRecMahout.getUser("579"));
			group.add(pumasGRecMahout.getUser("612"));
			group.add(pumasGRecMahout.getUser("94"));
			
//			group.add(pumasGRecMahout.getUser("236"));
//			group.add(pumasGRecMahout.getUser("579"));
//			group.add(pumasGRecMahout.getUser("612"));
//			group.add(pumasGRecMahout.getUser("94"));
			
			//For MOVIELENS_LATEST_REDUCED_N
//			group.add(pumasGRecMahout.getUser("52"));
//			group.add(pumasGRecMahout.getUser("82"));
//			group.add(pumasGRecMahout.getUser("119"));
			
			//For MOVIELENS_LATEST_REDUCED_O
//			group.add(pumasGRecMahout.getUser("215"));
//			group.add(pumasGRecMahout.getUser("272"));
//			group.add(pumasGRecMahout.getUser("617"));
			
			//For MOVIELENS_LATEST_REDUCED_P
//			group.add(pumasGRecMahout.getUser("138"));
//			group.add(pumasGRecMahout.getUser("173"));
//			group.add(pumasGRecMahout.getUser("181"));
			
			
			/*
			 * For (MOVIELENS_LATEST_REDUCED_M, MOVIELENS_LATEST_REDUCED_N, MOVIELENS_LATEST_REDUCED_O)
			 * InAll: [17148, 36619, 64178, 98791, 99602, 108914, 145354, 163611, 178312, 178998, 
			 * 			192829, 197905, 199866, 222595, 226955, 231515, 253782, 253816]
			 */			
//			group.add(pumasGRecMahout.getUser("17148"));
//			group.add(pumasGRecMahout.getUser("36619"));
//			group.add(pumasGRecMahout.getUser("64178"));
			
			
			makeRecommendation(pumasGRecMahout, group, exporter);

		} catch (SURException | ConfigurationException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

	}
}
