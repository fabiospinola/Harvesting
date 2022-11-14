package com.appdetex.harvest.main;

import com.appdetex.harvest.api.HarvestException;
import com.appdetex.harvest.api.MarketplaceDetection;
import com.appdetex.harvest.marketplace.Aliexpress.AliexpressHarvester;
import com.appdetex.harvest.marketplace.Amazon.AmazonUkHarvester;
import com.appdetex.harvest.marketplace.Amazon.AmazonUsHarvester;
import com.appdetex.harvest.marketplace.Ebay.EbayUkHarvester;
import com.appdetex.harvest.marketplace.Ebay.EbayUsHarvester;

import java.util.List;

import static com.appdetex.harvest.httpclient.methods.Operations.*;

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
        EbayUsHarvester harvEbayUs = new EbayUsHarvester();
        EbayUkHarvester harvEbayUk= new EbayUkHarvester();

        List<MarketplaceDetection> detections = null;
        List<MarketplaceDetection> detections1 = null;
        List<MarketplaceDetection> detections2 = null;
        List<MarketplaceDetection> detectionsEbayUs = null;
        List<MarketplaceDetection> detectionsEbayUk = null;
        try {
            //detections = harvest.parseTarget("jacuzzi", 3);
            //postADetection(detections);
            //detections1 = harvest1.parseTarget("jacuzzi", 3);
            //postADetection(detections1);
            //detections2 = harvest2.parseTarget("jacuzzi", 3);
            //postADetection(detections2);
            detectionsEbayUs = harvEbayUs.parseTarget("Jacuzzi", 5);
            postADetection(detectionsEbayUs);
            detectionsEbayUk = harvEbayUk.parseTarget("Jacuzzi", 5);
            postADetection(detectionsEbayUk);
        } catch (HarvestException  e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Got your data? Now leave me to rest!!! ( ͠° ͟ʖ ͡°)");
    }
}