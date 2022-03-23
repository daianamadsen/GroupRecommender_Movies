package edu.isistan.christian.recommenders.groups.movies.magres.pA;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import edu.isistan.christian.recommenders.groups.commons.datatypes.GRecGroup;
import edu.isistan.christian.recommenders.groups.commons.pumas.PUMASAgentProfile;
import edu.isistan.christian.recommenders.groups.magres.pA.MAGReSPA;
import edu.isistan.christian.recommenders.groups.magres.pA.MAGReSPAConfigs;
import edu.isistan.christian.recommenders.groups.magres.pA.MAGReSPAUserAg;
import edu.isistan.christian.recommenders.sur.datatypes.SURUser;
import edu.isistan.christian.recommenders.sur.movies.datatypes.MovieItem;

public abstract class MAGReSPAMovies extends MAGReSPA<MovieItem> {
		
	public MAGReSPAMovies (MAGReSPAConfigs<MovieItem> configs) {
		super(configs);
	}
	
	@Override
	protected List<MAGReSPAUserAg<MovieItem>> createAgents(GRecGroup group, Set<Set<MovieItem>> subsets) {
		List<MAGReSPAUserAg<MovieItem>> agents = new ArrayList<>();
		PUMASAgentProfile<MovieItem> agentsDefaultProfile = this.configs.getPUMASConfigs().getAgentsDefaultProfile();
		for (SURUser u : group){
			agents.add(new MAGReSPAUserAg<MovieItem>(u, agentsDefaultProfile.getInitPropStrategy(),
					agentsDefaultProfile.getConcessionCriterion(), agentsDefaultProfile.getPropAcceptanceStrategy(),
					agentsDefaultProfile.getUtilityFunction(), agentsDefaultProfile.getARPunishmentStrategy(),
					agentsDefaultProfile.getPPoolMaxProposalsAddedOnRefill(), agentsDefaultProfile.getPPoolMaxRefillsAllowed(),
					agentsDefaultProfile.isPPoolRefillAllowed(), agentsDefaultProfile.isPPoolAllowsRecycling(),
					agentsDefaultProfile.isOptReuseUnusedProposalsEnabled(), agentsDefaultProfile.isOptUtilityCacheEnabled(),
					subsets));
			
			//TODO change this... the utility function and the punishment strategy should be loaded from the configs object
		}
		return agents;
	}
	
	@Override
	protected List<MAGReSPAUserAg<MovieItem>> createAgents(GRecGroup group, 
			HashMap<SURUser, PUMASAgentProfile<MovieItem>> userAgProfiles, Set<Set<MovieItem>> subsets) {
		List<MAGReSPAUserAg<MovieItem>> agents = new ArrayList<>();
		
		PUMASAgentProfile<MovieItem> defaultProfile = this.configs.getPUMASConfigs().getAgentsDefaultProfile();
		
		for (SURUser u : group){
			PUMASAgentProfile<MovieItem> agProfile = (userAgProfiles.containsKey(u))? userAgProfiles.get(u) : defaultProfile;
			
			agents.add (new MAGReSPAUserAg<MovieItem>(u, agProfile.getInitPropStrategy(),
					agProfile.getConcessionCriterion(), agProfile.getPropAcceptanceStrategy(),
					agProfile.getUtilityFunction(), agProfile.getARPunishmentStrategy(),
					agProfile.getPPoolMaxProposalsAddedOnRefill(), agProfile.getPPoolMaxRefillsAllowed(),
					agProfile.isPPoolRefillAllowed(), agProfile.isPPoolAllowsRecycling(),
					agProfile.isOptReuseUnusedProposalsEnabled(), agProfile.isOptUtilityCacheEnabled(),
					subsets));
		}
		return agents;
	}

	@Override
	public String toString() {
		return "MAGReSPAMovies [singleUserRecommender="
				+ singleUserRecommender + ", groupRatingEstimationStrategy="
				+ groupRatingEstimationStrategy
				+ ", subsetGenStrategy= "+subsetGenStrategy
				+ ", configs=" + configs + "]";
	}

}
