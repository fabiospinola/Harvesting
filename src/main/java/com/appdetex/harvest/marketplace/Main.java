package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.api.HarvestException;
import com.appdetex.harvest.api.MarketplaceDetection;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        //
        System.out.println("Leave me alone, I'm Scraping!!! (╯ ͠° ͟ʖ ͡°)╯┻━┻");
        AliexpressHarvester harvest = new AliexpressHarvester();
        List<MarketplaceDetection> detections = null;
        try {
            detections = harvest.parseTarget("jacuzzi", 10);
        } catch (HarvestException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Got your data? Now leave me to rest!!! ( ͠° ͟ʖ ͡°)");
    }
}