package edu.isistan.christian.recommenders.groups.movies.magres.rA.commons;

import edu.isistan.christian.recommenders.sur.datatypes.SURUser;
import edu.isistan.christian.recommenders.sur.movies.MoviesSUR;
import edu.isistan.christian.recommenders.sur.movies.datatypes.MovieItem;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import edu.isistan.pumas.framework.protocols.commons.exceptions.CacheRefillingException;
import edu.isistan.pumas.framework.protocols.commons.userAgents.UserAgRecommenderBased;
import edu.isistan.pumas.framework.protocols.commons.userAgents.arPunishmentStrategy.AlreadyRatedPunishmentStrategy;
import edu.isistan.pumas.framework.protocols.commons.userAgents.concessionCriterion.ConcessionCriterion;
import edu.isistan.pumas.framework.protocols.commons.userAgents.initialProposalStrategy.InitialProposalStrategy;
import edu.isistan.pumas.framework.protocols.commons.userAgents.proposalAcceptanceStrategy.ProposalAcceptanceStrategy;
import edu.isistan.pumas.framework.protocols.commons.userAgents.utilityFunction.UtilityFunction;

public class MAGReSRAMoviesUserAgent extends UserAgRecommenderBased<MovieItem> {

//	private static final Logger logger = LogManager.getLogger(MoviesUserAg.class);

	public boolean lastCacheRefillSuccess = true;
		
	public MAGReSRAMoviesUserAgent(SURUser myUser,	InitialProposalStrategy<MovieItem> initialProposalStrategy,
			ConcessionCriterion<MovieItem> concessionCriterion, ProposalAcceptanceStrategy<MovieItem> proposalAcceptStrategy, 
			MoviesSUR itemRecSys,
			UtilityFunction<MovieItem> utilityFunction, AlreadyRatedPunishmentStrategy<MovieItem> arPunishmentStrategy,
			boolean proposalsPoolAllowsRecycling, boolean optReuseNotUsedProposalsEnabled, boolean optUtilityCacheEnabled) {
		super(myUser, initialProposalStrategy, concessionCriterion, proposalAcceptStrategy, itemRecSys, 
				utilityFunction, arPunishmentStrategy, proposalsPoolAllowsRecycling, optReuseNotUsedProposalsEnabled, 
				optUtilityCacheEnabled);
	}



	public MAGReSRAMoviesUserAgent(SURUser myUser, InitialProposalStrategy<MovieItem> initialProposalStrategy,
			ConcessionCriterion<MovieItem> concessionCriterion, ProposalAcceptanceStrategy<MovieItem> proposalAcceptStrategy, 
			MoviesSUR itemRecSys,
			UtilityFunction<MovieItem> utilityFunction, AlreadyRatedPunishmentStrategy<MovieItem> arPunishmentStrategy,
			int maxProposalsAddedOnRefill, int maxProposalsPoolRefillsAllowed,	boolean proposalsPoolIsRefillAllowed,
			boolean proposalsPoolAllowsRecycling, boolean optReuseNotUsedProposalsEnabled, boolean optUtilityCacheEnabled) {
		super(myUser, initialProposalStrategy, concessionCriterion, proposalAcceptStrategy, itemRecSys,
				utilityFunction, arPunishmentStrategy, maxProposalsAddedOnRefill, maxProposalsPoolRefillsAllowed, proposalsPoolIsRefillAllowed,
				proposalsPoolAllowsRecycling, optReuseNotUsedProposalsEnabled, optUtilityCacheEnabled);
	}



	@Override
	protected void refillProposalsPool() throws CacheRefillingException{
		if (lastCacheRefillSuccess){
			//Attempt to refill
			try{
				super.refillProposalsPool();
			} catch (CacheRefillingException e){
				//if refill failed => change the boolean and then throw the exception
				lastCacheRefillSuccess = false;
				throw e;
			}
		}
		else
			throw new CacheRefillingException();
	}

	
	public void reset(boolean fullReset){
		lastCacheRefillSuccess = true;
		super.reset(fullReset);
	}
	
	
}
