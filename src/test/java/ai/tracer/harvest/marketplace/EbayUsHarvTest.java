package ai.tracer.harvest.marketplace;

import ai.tracer.harvest.marketplace.ebay.EbayUsHarvester;

public class EbayUsHarvTest extends AbstractEbayHarvTest {
    public EbayUsHarvTest() {
        super("file:///Users/fabio.spinola/Documents/WebScraping/Harvesting/WebScraper/src/test/resources/eBayUs.html","file:///Users/fabio.spinola/Documents/WebScraping/Harvesting/WebScraper/src/test/resources/eBayUsEmpty.html", new EbayUsHarvester());
    }
}
