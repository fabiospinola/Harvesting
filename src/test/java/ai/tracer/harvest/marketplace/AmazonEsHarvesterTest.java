package ai.tracer.harvest.marketplace;

import ai.tracer.harvest.marketplace.amazonplaywright.AmazonESHarvester;

public class AmazonEsHarvesterTest extends AbstractAmazonEUHarvesterTest {

    public AmazonEsHarvesterTest(){
        super(new AmazonESHarvester(), "file:///Users/fabio.spinola/Documents/WebScraping/Harvesting/WebScraper/src/test/resources/amazonEsSearch.html","file:///Users/fabio.spinola/Documents/WebScraping/Harvesting/WebScraper/src/test/resources/amazonEsItem.html");
    }
}
