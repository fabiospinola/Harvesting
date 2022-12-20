package ai.tracer.harvest.marketplace;

import ai.tracer.harvest.marketplace.ebay.EbayUkHarvester;

public class EbayUkHarvTest extends AbstractEbayHarvTest {
    public EbayUkHarvTest() {
        super("file:src/test/resources/eBayUk.html", "file:src/test/resources/eBayUkEmpty.html", new EbayUkHarvester());
    }
}
