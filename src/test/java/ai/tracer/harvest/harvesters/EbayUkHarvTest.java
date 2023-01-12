package ai.tracer.harvest.harvesters;

import ai.tracer.harvest.harvesters.ebay.EbayUkHarvester;

public class EbayUkHarvTest extends AbstractEbayHarvTest {
    public EbayUkHarvTest() {
        super("file:src/test/resources/eBayUk.html", "file:src/test/resources/eBayUkEmpty.html", new EbayUkHarvester());
    }
}
