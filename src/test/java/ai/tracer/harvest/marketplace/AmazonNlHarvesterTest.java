package ai.tracer.harvest.marketplace;

import ai.tracer.harvest.marketplace.amazonplaywright.AmazonNlHarvester;

public class AmazonNlHarvesterTest extends AbstractAmazonPlaywrightTest {
    public AmazonNlHarvesterTest(){
        super(new AmazonNlHarvester(),"file:///Users/fabio.spinola/Documents/WebScraping/Harvesting/WebScraper/src/test/resources/amazonNlSearch.html","file:///Users/fabio.spinola/Documents/WebScraping/Harvesting/WebScraper/src/test/resources/amazonNlItem.html");
    }

}
