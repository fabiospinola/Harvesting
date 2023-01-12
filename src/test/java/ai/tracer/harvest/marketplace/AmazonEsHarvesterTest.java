package ai.tracer.harvest.marketplace;

import ai.tracer.harvest.marketplace.amazonplaywright.AmazonEsHarvester;

public class AmazonEsHarvesterTest extends AbstractAmazonPlaywrightTest {

    public AmazonEsHarvesterTest(){
        super(new AmazonEsHarvester(), "file:///Users/fabio.spinola/Documents/WebScraping/Harvesting/WebScraper/src/test/resources/amazonEsSearch.html","file:///Users/fabio.spinola/Documents/WebScraping/Harvesting/WebScraper/src/test/resources/amazonEsItem.html");
    }
}
