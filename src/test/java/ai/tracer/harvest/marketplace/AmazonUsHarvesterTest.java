package ai.tracer.harvest.marketplace;

import ai.tracer.harvest.marketplace.Amazon.AmazonUsHarvester;

public class AmazonUsHarvesterTest extends AbstractAmazonHarvesterTest {

    public AmazonUsHarvesterTest(){

        super (new AmazonUsHarvester() ,"/amazonUsSearch.html", "/amazonUsItem.html");
    }
}
