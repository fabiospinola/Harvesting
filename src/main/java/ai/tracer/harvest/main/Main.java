package ai.tracer.harvest.main;

import ai.tracer.harvest.httpclient.methods.Operations;
import ai.tracer.harvest.marketplace.AmazonPlaywright.AmazonESHarvester;
import ai.tracer.harvest.marketplace.AmazonPlaywright.AmazonNLHarvester;
import ai.tracer.harvest.api.MarketplaceDetection;
import ai.tracer.harvest.marketplace.Amazon.AmazonUkHarvester;
import ai.tracer.harvest.marketplace.Amazon.AmazonUsHarvester;
import ai.tracer.harvest.marketplace.Ebay.EbayUkHarvester;
import ai.tracer.harvest.marketplace.Ebay.EbayUsHarvester;

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
        String searchTerm = "jacuzzi";
        int numItems = 5;

        try {
            detectionsAES = harvestAES.parseTarget(searchTerm, numItems);
            Operations.postADetection(detectionsAES);
            detectionsANL = harvestANL.parseTarget(searchTerm, numItems);
            Operations.postADetection(detectionsANL);
            detectionsAUS = harvestAUS.parseTarget(searchTerm, numItems);
            Operations.postADetection(detectionsAUS);
            detectionsAUK = harvestAUK.parseTarget(searchTerm, numItems);
            Operations.postADetection(detectionsAUK);
            detectionsEUS = harvestEUS.parseTarget(searchTerm, numItems);
            Operations.postADetection(detectionsEUS);
            detectionsEUK = harvestEUK.parseTarget(searchTerm, numItems);
            Operations.postADetection(detectionsEUK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Got your data? Now leave me to rest!!! ( ͠° ͟ʖ ͡°)");
    }
}