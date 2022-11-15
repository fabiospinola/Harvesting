package com.appdetex.harvest.main;

import com.appdetex.harvest.api.MarketplaceDetection;
import com.appdetex.harvest.marketplace.Aliexpress.AliexpressHarvester;
import com.appdetex.harvest.marketplace.Amazon.AmazonUkHarvester;
import com.appdetex.harvest.marketplace.Amazon.AmazonUsHarvester;
import com.appdetex.harvest.marketplace.Ebay.EbayUkHarvester;
import com.appdetex.harvest.marketplace.Ebay.EbayUsHarvester;

import java.util.List;

import static com.appdetex.harvest.httpclient.methods.Operations.postADetection;


public class Main {

    public static void main(String[] args) throws Exception {

        System.out.println("Leave me alone, I'm Scraping!!! (╯ ͠° ͟ʖ ͡°)╯┻━┻");
        //parameterizedGet(25);
        //simpleGet(); //Prints DB content in json (ugly) format
        //parameterizedGet(122);
        //deleteDetection(123);

        AliexpressHarvester harvest = new AliexpressHarvester();
        AmazonUsHarvester harvest1 = new AmazonUsHarvester();
        AmazonUkHarvester harvest2 = new AmazonUkHarvester();
        EbayUsHarvester harvest3 = new EbayUsHarvester();
        EbayUkHarvester harvest4 = new EbayUkHarvester();
        List<MarketplaceDetection> detections = null;
        List<MarketplaceDetection> detections1 = null;
        List<MarketplaceDetection> detections2 = null;
        List<MarketplaceDetection> detections3 = null;
        List<MarketplaceDetection> detections4 = null;
        String searchTerm = "jacuzzi";
        int numItems = 2;

        try {
            detections = harvest.parseTarget(searchTerm, numItems);
            postADetection(detections);
            detections1 = harvest1.parseTarget(searchTerm, numItems);
            postADetection(detections1);
            detections2 = harvest2.parseTarget(searchTerm, numItems);
            postADetection(detections2);
            detections3 = harvest3.parseTarget(searchTerm, numItems);
            postADetection(detections3);
            detections4 = harvest4.parseTarget(searchTerm, numItems);
            postADetection(detections4);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Got your data? Now leave me to rest!!! ( ͠° ͟ʖ ͡°)");
    }
}