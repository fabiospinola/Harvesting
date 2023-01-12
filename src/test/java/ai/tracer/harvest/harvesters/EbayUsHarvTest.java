package ai.tracer.harvest.harvesters;

import ai.tracer.harvest.harvesters.ebay.EbayUsHarvester;

public class EbayUsHarvTest extends AbstractEbayHarvTest {
    public EbayUsHarvTest() {
        super("file:src/test/resources/eBayUs.html","file:src/test/resources/eBayUsEmpty.html", new EbayUsHarvester());
    }
}
