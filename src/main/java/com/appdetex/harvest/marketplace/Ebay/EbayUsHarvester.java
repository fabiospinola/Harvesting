package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.api.HarvestException;
import com.appdetex.harvest.api.MarketplaceDetection;

import java.util.List;

public class EbayUsHarvester extends AbstractEbayHarvester {
    public EbayUsHarvester() {
        super("https://www.ebay.com/sch/i.html?_nkw=%s");
    }

}
