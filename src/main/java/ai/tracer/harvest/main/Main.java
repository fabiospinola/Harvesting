package ai.tracer.harvest.main;

import ai.tracer.harvest.httpclient.Operations;
import ai.tracer.harvest.marketplace.AmazonPlaywright.AmazonESHarvester;
import ai.tracer.harvest.marketplace.AmazonPlaywright.AmazonNLHarvester;
import ai.tracer.harvest.api.MarketplaceDetection;
import ai.tracer.harvest.marketplace.Amazon.AmazonUkHarvester;
import ai.tracer.harvest.marketplace.Amazon.AmazonUsHarvester;
import ai.tracer.harvest.marketplace.Ebay.EbayUkHarvester;
import ai.tracer.harvest.marketplace.Ebay.EbayUsHarvester;

import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Leave me alone, I'm Scraping!!! (╯ ͠° ͟ʖ ͡°)╯┻━┻");

        AmazonESHarvester harvestAES = new AmazonESHarvester();
        AmazonNLHarvester harvestANL = new AmazonNLHarvester();
        AmazonUsHarvester harvestAUS = new AmazonUsHarvester();
        AmazonUkHarvester harvestAUK = new AmazonUkHarvester();
        EbayUsHarvester harvestEUS = new EbayUsHarvester();
        EbayUkHarvester harvestEUK = new EbayUkHarvester();
        List<MarketplaceDetection> detectionsAES = null;
        List<MarketplaceDetection> detectionsANL = null;
        List<MarketplaceDetection> detectionsAUS = null;
        List<MarketplaceDetection> detectionsAUK = null;
        List<MarketplaceDetection> detectionsEUS = null;
        List<MarketplaceDetection> detectionsEUK = null;

        ArrayList<String> brandTracks = Operations.getBrandTracks();
        ArrayList<Long> customer_ids = Operations.getCustomerIds();

        for (int i = 0; i < brandTracks.size(); i++) {
            int numItems = 3;
            try {
                detectionsAES = harvestAES.parseTarget(brandTracks.get(i), numItems,customer_ids.get(i));
                Operations.postADetection(detectionsAES);
                detectionsANL = harvestANL.parseTarget(brandTracks.get(i), numItems,customer_ids.get(i));
                Operations.postADetection(detectionsANL);
                detectionsAUS = harvestAUS.parseTarget(brandTracks.get(i), numItems,customer_ids.get(i));
                Operations.postADetection(detectionsAUS);
                detectionsAUK = harvestAUK.parseTarget(brandTracks.get(i), numItems,customer_ids.get(i));
                Operations.postADetection(detectionsAUK);
                detectionsEUS = harvestEUS.parseTarget(brandTracks.get(i), numItems,customer_ids.get(i));
                Operations.postADetection(detectionsEUS);
                detectionsEUK = harvestEUK.parseTarget(brandTracks.get(i), numItems,customer_ids.get(i));
                Operations.postADetection(detectionsEUK);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("Got your data? Now leave me to rest!!! ( ͠° ͟ʖ ͡°)");
        }

    }
}