package edu.isistan.christian.recommenders.groups.movies.utils;

import java.util.ArrayList;
import java.util.List;

import edu.isistan.christian.recommenders.groups.commons.datatypes.GRecRecommendation;
import edu.isistan.christian.recommenders.groups.commons.datatypes.GRecResult;
import edu.isistan.christian.recommenders.groups.utils.CSVResultsExporter;
import edu.isistan.christian.recommenders.sur.movies.datatypes.MovieItem;

public class MoviesCSVResultsExporter extends CSVResultsExporter<MovieItem> {

	protected List<String> getItemHeaderData(GRecResult<MovieItem> result){
		List<String> itemHeaderData = new ArrayList<>();
		itemHeaderData.add("Movie ID");
		itemHeaderData.add("Title");
		itemHeaderData.add("Genres");
		
		return itemHeaderData;
	}
	
	protected List<String> getItemRowData(GRecRecommendation<MovieItem> rec){
		List<String> itemRowData = new ArrayList<>();
		itemRowData.add(rec.getRecommendedItem().getID());
		itemRowData.add(rec.getRecommendedItem().getTitle());
		itemRowData.add(rec.getRecommendedItem().getGenres().toString());
		return itemRowData;
	}
	
	protected List<String> getUserRowData(GRecRecommendation<MovieItem> rec, GRecResult<MovieItem> result){
		return super.getUserRowData(rec, result);
	}
	
}
