package ai.tracer.harvest.marketplace;

import ai.tracer.harvest.marketplace.amazonplaywright.AmazonNLHarvester;

public class AmazonNlHarvesterTest extends AbstractAmazonEUHarvesterTest {
    public AmazonNlHarvesterTest(){
        super(new AmazonNLHarvester(),"jacuzzi","jacuzzi");
    }

}
