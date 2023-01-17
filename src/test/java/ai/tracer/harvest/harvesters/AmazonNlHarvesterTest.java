package ai.tracer.harvest.harvesters;

import ai.tracer.harvest.harvesters.amazonplaywright.AmazonNlHarvester;

public class AmazonNlHarvesterTest extends AbstractAmazonEuTest {

    public AmazonNlHarvesterTest(){
        super(new AmazonNlHarvester(),"src/test/resources/amazonNlSearch.html","src/test/resources/amazonNlItem.html");
    }

}
