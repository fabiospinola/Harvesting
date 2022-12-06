package ai.tracer.harvest.main;

import ai.tracer.harvest.httpclient.Operations;
import ai.tracer.harvest.marketplace.AmazonPlaywright.AmazonESHarvester;
import ai.tracer.harvest.marketplace.AmazonPlaywright.AmazonNLHarvester;
import ai.tracer.harvest.api.MarketplaceDetection;
import ai.tracer.harvest.marketplace.Amazon.AmazonUkHarvester;
import ai.tracer.harvest.marketplace.Amazon.AmazonUsHarvester;
import ai.tracer.harvest.marketplace.Ebay.EbayUkHarvester;
import ai.tracer.harvest.marketplace.Ebay.EbayUsHarvester;
import ai.tracer.harvest.rulesengine.RulesEngine;

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
        ArrayList<String> searchterms = Operations.getSearchTerm();
        ArrayList<String> terms = new ArrayList<>();

        for (int i = 0; i < searchterms.size(); i++) {
            terms.add(searchterms.get(0).split("\",\"")[i]);
        }
        for (int i = 0; i < terms.size(); i++) {
            String searchTerm = terms.get(i).replace("[", "").replace("]", "").replace("\"", "");
            int numItems = 10;
            try {
                /*detectionsAES = harvestAES.parseTarget(searchTerm, numItems);
                detectionsAES = RulesEngine.verifyDescription(detectionsAES,"inflable","enforcing");
                detectionsAES = RulesEngine.verifyDescription(detectionsAES,"Jacuzzi®","fair-use");
                Operations.postADetection(detectionsAES);
                //Operations.postADetection(RulesEngine.verifyDescription(detectionsAES,"hinchable","enforcing"));
                detectionsANL = harvestANL.parseTarget(searchTerm, numItems);
                detectionsANL = RulesEngine.verifyDescription(detectionsANL,"inflatable","enforcing");
                detectionsANL = RulesEngine.verifyDescription(detectionsANL,"Jacuzzi®","fair-use");
                Operations.postADetection(detectionsANL);
                detectionsAUS = harvestAUS.parseTarget(searchTerm, numItems);
                detectionsAUS =RulesEngine.verifyDescription(detectionsAUS, "inflatable","enforcing");
                detectionsAUS =RulesEngine.verifyDescription(detectionsAUS, "Jacuzzi®","fair-use");
                Operations.postADetection(detectionsAUS);
                detectionsAUK = harvestAUK.parseTarget(searchTerm, numItems);
                detectionsAUK =RulesEngine.verifyDescription(detectionsAUK, "inflatable","enforcing");
                detectionsAUK =RulesEngine.verifyDescription(detectionsAUK, "Jacuzzi®","fair-use");
                Operations.postADetection(detectionsAUK);-*/
                detectionsEUS = harvestEUS.parseTarget(searchTerm, numItems);
                detectionsEUS =RulesEngine.verifyDescription(detectionsEUS, "inflatable","enforcing");
                detectionsEUS =RulesEngine.verifyDescription(detectionsEUS, "Jacuzzi®","fair-use");
                Operations.postADetection(detectionsEUS);
                detectionsEUK = harvestEUK.parseTarget(searchTerm, numItems);
                detectionsEUK =RulesEngine.verifyDescription(detectionsEUK, "inflatable","enforcing");
                detectionsEUK =RulesEngine.verifyDescription(detectionsEUK, "Jacuzzi®","fair-use");
                Operations.postADetection(detectionsEUK);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Got your data? Now leave me to rest!!! ( ͠° ͟ʖ ͡°)");
    }
}