package ai.tracer.harvest.marketplace;

import ai.tracer.harvest.marketplace.amazon.AmazonUkHarvester;

public class AmazonUkHarvesterTest extends AbstractAmazonHarvesterTest {

    public AmazonUkHarvesterTest () {
        super(new AmazonUkHarvester(),"/amazonUkSearch.html", "/amazonUkItem.html");
    }
}
