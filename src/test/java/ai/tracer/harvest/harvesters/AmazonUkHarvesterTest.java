package ai.tracer.harvest.harvesters;

import ai.tracer.harvest.harvesters.amazon.AmazonUkHarvester;

public class AmazonUkHarvesterTest extends AbstractAmazonHarvesterTest {

    public AmazonUkHarvesterTest () {

        super(new AmazonUkHarvester(),"/amazonUkSearch.html", "/amazonUkItem.html");
    }
}
