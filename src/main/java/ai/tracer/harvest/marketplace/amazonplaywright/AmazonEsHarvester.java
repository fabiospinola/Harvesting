package ai.tracer.harvest.marketplace.amazonplaywright;

import ai.tracer.harvest.api.MarketplaceDetection;

import java.util.List;

public class AmazonEsHarvester extends AbstractAmazonPlaywright {

    public AmazonEsHarvester(){
        super("https://www.amazon.es/s?k=","https://www.amazon.es");
        //super("file:///Users/fabio.spinola/Documents/WebScraping/Harvesting/WebScraper/src/test/resources/amazonEsSearch.html","https://www.amazon.es");
    }


    public List<MarketplaceDetection> parseTargetTest(String term, int numItems, Long customer_id) {
        return null;
    }
}
