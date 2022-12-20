package ai.tracer.harvest.marketplace;

import ai.tracer.harvest.marketplace.amazonplaywright.AmazonESHarvester;

public class AmazonEsHarvesterTest extends AbstractAmazonEUTest{

    public AmazonEsHarvesterTest(){
        super(new AmazonESHarvester(), "file:src/test/resources/amazonEsSearch.html","file:src/test/resources/amazonEsItem.html");
    }
}
