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
	
	@Override
	protected List<UserAg<MovieItem>> createAgents(GRecGroup group, HashMap<SURUser, Double> assertivenessFactors, HashMap<SURUser, Double> cooperativenessFactors, HashMap<SURUser, HashMap<SURUser, Double>> relationshipsFactors) {
		List<UserAg<MovieItem>> agents = new ArrayList<>();
		PUMASAgentProfile<MovieItem> agentsDefaultProfile = this.configs.getPUMASConfigs().getAgentsDefaultProfile();
		for (SURUser u : group){

			UserAg<MovieItem> ua = new MAGReSRAMoviesUserAgent(u, agentsDefaultProfile.getInitPropStrategy(),
					agentsDefaultProfile.getConcessionCriterion(), agentsDefaultProfile.getPropAcceptanceStrategy(),
					(MoviesSUR) configs.getPUMASConfigs().getSUR(),
					agentsDefaultProfile.getUtilityFunction(), agentsDefaultProfile.getARPunishmentStrategy(),
					agentsDefaultProfile.getPPoolMaxProposalsAddedOnRefill(), agentsDefaultProfile.getPPoolMaxRefillsAllowed(),
					agentsDefaultProfile.isPPoolRefillAllowed(), agentsDefaultProfile.isPPoolAllowsRecycling(),
					agentsDefaultProfile.isOptReuseUnusedProposalsEnabled(), agentsDefaultProfile.isOptUtilityCacheEnabled());

			ua.setAssertivenessFactor(assertivenessFactors.get(u));
			ua.setCooperativenessFactor(cooperativenessFactors.get(u));
			ua.setRelationshipsFactor(relationshipsFactors.get(u));

			agents.add (ua);
			
			//TODO change this... the utility function and the punishment strategy should be loaded from the configs object
		}
		return agents;
	}
	
	@Override
	protected List<UserAg<MovieItem>> createAgents(GRecGroup group, HashMap<SURUser, PUMASAgentProfile<MovieItem>> userAgProfiles, HashMap<SURUser, Double> assertivenessFactors, HashMap<SURUser, Double> cooperativenessFactors, HashMap<SURUser, HashMap<SURUser, Double>> relationshipsFactors) {
		List<UserAg<MovieItem>> agents = new ArrayList<>();
		
		PUMASAgentProfile<MovieItem> defaultProfile = this.configs.getPUMASConfigs().getAgentsDefaultProfile();
		
		for (SURUser u : group){
			PUMASAgentProfile<MovieItem> agProfile = (userAgProfiles.containsKey(u))? userAgProfiles.get(u) : defaultProfile;
			
			UserAg<MovieItem> ua = new MAGReSRAMoviesUserAgent(u, agProfile.getInitPropStrategy(),
					agProfile.getConcessionCriterion(), agProfile.getPropAcceptanceStrategy(),
					(MoviesSUR) configs.getPUMASConfigs().getSUR(),
					agProfile.getUtilityFunction(), agProfile.getARPunishmentStrategy(),
					agProfile.getPPoolMaxProposalsAddedOnRefill(), agProfile.getPPoolMaxRefillsAllowed(),
					agProfile.isPPoolRefillAllowed(), agProfile.isPPoolAllowsRecycling(),
					agProfile.isOptReuseUnusedProposalsEnabled(), agProfile.isOptUtilityCacheEnabled());

			ua.setAssertivenessFactor(assertivenessFactors.get(u));
			ua.setCooperativenessFactor(cooperativenessFactors.get(u));
			ua.setRelationshipsFactor(relationshipsFactors.get(u));

			agents.add (ua);
		}
		return agents;
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
