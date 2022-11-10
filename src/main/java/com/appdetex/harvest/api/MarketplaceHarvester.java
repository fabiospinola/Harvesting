package com.appdetex.harvest.api;

import java.io.IOException;
import java.util.List;

public interface MarketplaceHarvester {

    /**
     * Parse the marketplace target and return the number of items requested
     * @param term the search term
     * @param numItems the number of items to return
     * @return returns detections found for the search term
     * @throws HarvestException
     */

    List<MarketplaceDetection> parseAliexpress(String term, int numItems) throws HarvestException, InterruptedException, IOException;
}
