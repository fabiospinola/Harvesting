package ai.tracer.harvest.harvesters;

import ai.tracer.harvest.harvesters.amazonplaywright.AmazonEsHarvester;

public class AmazonEsHarvesterTest extends AbstractAmazonEuTest {

    public AmazonEsHarvesterTest(){
        super(new AmazonEsHarvester(), "src/test/resources/amazonEsSearch.html","src/test/resources/amazonEsItem.html");
    }
}
