package com.appdetex.harvest.api;

import java.util.List;

public interface MarketplaceHarvester {

    /**
     * Parse the marketplace target and return the number of items requested
     * @param searchTerm the search term
     * @param numItems the number of items to return
     * @return return detections found for the search terms
     * @throws HarvestException
     */

    List<MarketplaceDetection> parseTarget(String searchTerm, int numItems) throws HarvestException;
}
