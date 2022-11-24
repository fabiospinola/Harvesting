package ai.tracer.harvest.marketplace;

import ai.tracer.harvest.marketplace.Ebay.EbayUkHarvester;

public class EbayUkHarvTest extends AbstractEbayHarvTest {
    public EbayUkHarvTest() {
        super("file:///Users/fabio.spinola/Documents/WebScraping/Harvesting/WebScraper/src/test/resources/eBayUk.html", "file:///Users/fabio.spinola/Documents/WebScraping/Harvesting/WebScraper/src/test/resources/eBayUkEmpty.html", new EbayUkHarvester());
    }
}
