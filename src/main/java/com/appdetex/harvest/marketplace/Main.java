package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.api.HarvestException;
import com.appdetex.harvest.api.MarketplaceDetection;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        //
        System.out.println("Leave me alone, I'm Scraping!!! (╯ ͠° ͟ʖ ͡°)╯┻━┻");
        AliexpressHarvester harvest = new AliexpressHarvester();
        AmazonUsHarvester harvest1 = new AmazonUsHarvester();
        AmazonUkHarvester harvest2 = new AmazonUkHarvester();
        List<MarketplaceDetection> detections = null;
        List<MarketplaceDetection> detections1 = null;
        List<MarketplaceDetection> detections2 = null;
        try {
            detections = harvest.parseTarget("jacuzzi", 10);
            detections1 = harvest1.parseTarget("jacuzzi", 10);
            detections2 = harvest2.parseTarget("jacuzzi", 10);
        } catch (HarvestException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Got your data? Now leave me to rest!!! ( ͠° ͟ʖ ͡°)");
    }
}