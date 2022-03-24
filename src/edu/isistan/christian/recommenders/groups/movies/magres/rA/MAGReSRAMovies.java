package edu.isistan.christian.recommenders.groups.movies.magres.rA;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.isistan.christian.recommenders.groups.commons.datatypes.GRecGroup;
import edu.isistan.christian.recommenders.groups.commons.pumas.PUMASAgentProfile;
import edu.isistan.christian.recommenders.groups.magres.rA.MAGReSRA;
import edu.isistan.christian.recommenders.groups.magres.rA.MAGReSRAConfigs;
import edu.isistan.christian.recommenders.groups.movies.magres.rA.commons.MAGReSRAMoviesUserAgent;
import edu.isistan.christian.recommenders.sur.datatypes.SURUser;
import edu.isistan.christian.recommenders.sur.movies.MoviesSUR;
import edu.isistan.christian.recommenders.sur.movies.datatypes.MovieItem;
import edu.isistan.pumas.framework.protocols.commons.userAgents.UserAg;

public abstract class MAGReSRAMovies extends MAGReSRA<MovieItem> {
	
	public MAGReSRAMovies (MAGReSRAConfigs<MovieItem> configs) {
		super(configs);
	}
	
	@Override
	protected List<UserAg<MovieItem>> createAgents(GRecGroup group) {
		List<UserAg<MovieItem>> agents = new ArrayList<>();
		PUMASAgentProfile<MovieItem> agentsDefaultProfile = this.configs.getPUMASConfigs().getAgentsDefaultProfile();
		for (SURUser u : group){
			agents.add (new MAGReSRAMoviesUserAgent(u, agentsDefaultProfile.getInitPropStrategy(),
					agentsDefaultProfile.getConcessionCriterion(), agentsDefaultProfile.getPropAcceptanceStrategy(),
					(MoviesSUR) configs.getPUMASConfigs().getSUR(),
					agentsDefaultProfile.getUtilityFunction(), agentsDefaultProfile.getARPunishmentStrategy(),
					agentsDefaultProfile.getPPoolMaxProposalsAddedOnRefill(), agentsDefaultProfile.getPPoolMaxRefillsAllowed(),
					agentsDefaultProfile.isPPoolRefillAllowed(), agentsDefaultProfile.isPPoolAllowsRecycling(),
					agentsDefaultProfile.isOptReuseUnusedProposalsEnabled(), agentsDefaultProfile.isOptUtilityCacheEnabled()));
			
			//TODO change this... the utility function and the punishment strategy should be loaded from the configs object
		}
		return agents;
	}
	
	@Override
	protected List<UserAg<MovieItem>> createAgents(GRecGroup group, HashMap<SURUser, PUMASAgentProfile<MovieItem>> userAgProfiles) {
		List<UserAg<MovieItem>> agents = new ArrayList<>();
		
		PUMASAgentProfile<MovieItem> defaultProfile = this.configs.getPUMASConfigs().getAgentsDefaultProfile();
		
		for (SURUser u : group){
			PUMASAgentProfile<MovieItem> agProfile = (userAgProfiles.containsKey(u))? userAgProfiles.get(u) : defaultProfile;
			
			agents.add (new MAGReSRAMoviesUserAgent(u, agProfile.getInitPropStrategy(),
					agProfile.getConcessionCriterion(), agProfile.getPropAcceptanceStrategy(),
					(MoviesSUR) configs.getPUMASConfigs().getSUR(),
					agProfile.getUtilityFunction(), agProfile.getARPunishmentStrategy(),
					agProfile.getPPoolMaxProposalsAddedOnRefill(), agProfile.getPPoolMaxRefillsAllowed(),
					agProfile.isPPoolRefillAllowed(), agProfile.isPPoolAllowsRecycling(),
					agProfile.isOptReuseUnusedProposalsEnabled(), agProfile.isOptUtilityCacheEnabled()));
		}
		return agents;
	}

	private List<UserAg<MovieItem>> setFactors(List<UserAg<MovieItem>> agents, HashMap<SURUser, Double> assertivenessFactors, HashMap<SURUser, Double> cooperativenessFactors, HashMap<SURUser, HashMap<SURUser, Double>> relationshipsFactors) {
		if (assertivenessFactors != null || cooperativenessFactors != null || relationshipsFactors != null) {
			HashMap<String, SURUser> userMapping = new HashMap<String, SURUser>();
			for (UserAg<MovieItem> ua : agents) {
				SURUser u = ua.getRepresentedUser();
				if (assertivenessFactors != null) {
					ua.setAssertivenessFactor(assertivenessFactors.get(u));
				}
				if (cooperativenessFactors != null) {
					ua.setCooperativenessFactor(cooperativenessFactors.get(u));
				}
				userMapping.put(ua.getID(), ua.getRepresentedUser());
			}
			for (UserAg<MovieItem> ua : agents) {
				HashMap<String, Double> rsFactors = new HashMap<String, Double>();
				SURUser u1 = userMapping.get(ua.getID());
				HashMap<SURUser, Double> rsU1 = relationshipsFactors.get(u1);
				for (UserAg<MovieItem> otherUa : agents) {
					SURUser u2 = userMapping.get(otherUa.getID());
					rsFactors.put(otherUa.getID(), rsU1.get(u2));
				}
				ua.setRelationshipsFactor(rsFactors);
			}
		}
		return agents;
	}
	
	@Override
	protected List<UserAg<MovieItem>> createAgents(GRecGroup group, HashMap<SURUser, Double> assertivenessFactors, HashMap<SURUser, Double> cooperativenessFactors, HashMap<SURUser, HashMap<SURUser, Double>> relationshipsFactors) {
		List<UserAg<MovieItem>> agents = createAgents(group);
		return setFactors(agents, assertivenessFactors, cooperativenessFactors, relationshipsFactors);
	}
	
	@Override
	protected List<UserAg<MovieItem>> createAgents(GRecGroup group, HashMap<SURUser, PUMASAgentProfile<MovieItem>> userAgProfiles, HashMap<SURUser, Double> assertivenessFactors, HashMap<SURUser, Double> cooperativenessFactors, HashMap<SURUser, HashMap<SURUser, Double>> relationshipsFactors) {
		List<UserAg<MovieItem>> agents = createAgents(group, userAgProfiles);
		return setFactors(agents, assertivenessFactors, cooperativenessFactors, relationshipsFactors);
	}

	@Override
	public String toString() {
		return "MAGReSRAMovies [singleUserRecommender="
				+ singleUserRecommender + ", groupRatingEstimationStrategy="
				+ groupRatingEstimationStrategy + ", configs=" + configs + "]";
	}

	/**
	 * @param title
	 * @return a list with the movies with title matching {@literal 'title'}
	 */
	public List<MovieItem> getMoviesByTitle(String title){
		List<MovieItem> movies = singleUserRecommender.getAllItems();
		List<MovieItem> result = new ArrayList<>();
		
		for (int i=0; i<movies.size();i++){
			if (movies.get(i).getTitle().equals(title)) 
				result.add(movies.get(i));
		}
		return result;
	}
	
}
