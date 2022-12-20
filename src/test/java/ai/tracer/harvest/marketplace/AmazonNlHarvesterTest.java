package ai.tracer.harvest.marketplace;

import ai.tracer.harvest.marketplace.amazonplaywright.AmazonNLHarvester;

public class AmazonNlHarvesterTest extends AbstractAmazonEUTest{
    public AmazonNlHarvesterTest(){
        super(new AmazonNLHarvester(),"file:src/test/resources/amazonNlSearch.html","file:src/test/resources/amazonNlItem.html");
    }

}
