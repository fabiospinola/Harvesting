package com.appdetex.harvest.main;

import com.appdetex.harvest.api.MarketplaceDetection;
import com.appdetex.harvest.marketplace.Amazon.AmazonUkHarvester;
import com.appdetex.harvest.marketplace.Amazon.AmazonUsHarvester;
import com.appdetex.harvest.marketplace.AmazonPlaywright.AmazonESHarvester;
import com.appdetex.harvest.marketplace.AmazonPlaywright.AmazonNLHarvester;
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

        AmazonESHarvester harvestAES = new AmazonESHarvester();
        AmazonNLHarvester harvestANL = new AmazonNLHarvester();
        AmazonUsHarvester harvestAUS = new AmazonUsHarvester();
        AmazonUkHarvester harvestAUK = new AmazonUkHarvester();
        EbayUsHarvester harvestEUS = new EbayUsHarvester();
        EbayUkHarvester harvestEUK = new EbayUkHarvester();
        List<MarketplaceDetection> detections = null;
        List<MarketplaceDetection> detections1 = null;
        List<MarketplaceDetection> detections2 = null;
        List<MarketplaceDetection> detections3 = null;
        List<MarketplaceDetection> detections4 = null;
        List<MarketplaceDetection> detections5 = null;
        String searchTerm = "jacuzzi";
        int numItems = 10;

        try {
            detections = harvestAES.parseTarget(searchTerm, numItems);
            postADetection(detections);
            detections1 = harvestANL.parseTarget(searchTerm, numItems);
            postADetection(detections1);
            detections2 = harvestAUS.parseTarget(searchTerm, numItems);
            postADetection(detections2);
            detections3 = harvestAUK.parseTarget(searchTerm, numItems);
            postADetection(detections3);
            detections4 = harvestEUS.parseTarget(searchTerm, numItems);
            postADetection(detections4);
            detections5 = harvestEUK.parseTarget(searchTerm, numItems);
            postADetection(detections5);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Got your data? Now leave me to rest!!! ( ͠° ͟ʖ ͡°)");
    }
}