package ai.tracer.harvest.marketplace;

import ai.tracer.harvest.marketplace.ebay.EbayUsHarvester;

public class EbayUsHarvTest extends AbstractEbayHarvTest {
    public EbayUsHarvTest() {
        super("file:src/test/resources/eBayUs.html","file:src/test/resources/eBayUsEmpty.html", new EbayUsHarvester());
    }
}
