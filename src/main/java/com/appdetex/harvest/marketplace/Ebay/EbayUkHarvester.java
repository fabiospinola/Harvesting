package com.appdetex.harvest.marketplace.Ebay;

import com.appdetex.harvest.api.HarvestException;
import com.appdetex.harvest.api.MarketplaceDetection;

import java.util.List;

public class EbayUkHarvester extends AbstractEbayHarvester{
    public EbayUkHarvester() {
        super("https://www.ebay.co.uk/sch/i.html?_nkw=");
    }

}
