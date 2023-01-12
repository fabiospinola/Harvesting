package ai.tracer.harvest.harvesters;

import ai.tracer.harvest.harvesters.amazon.AmazonUsHarvester;

public class AmazonUsHarvesterTest extends AbstractAmazonHarvesterTest {

    public AmazonUsHarvesterTest(){

        super (new AmazonUsHarvester() ,"/amazonUsSearch.html", "/amazonUsItem.html");
    }
}
