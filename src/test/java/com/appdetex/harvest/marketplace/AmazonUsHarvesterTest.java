package com.appdetex.harvest.marketplace;

public class AmazonUsHarvesterTest extends AbstractAmazonHarvesterTest {

    public AmazonUsHarvesterTest(){

        super (new AmazonUsHarvester() ,"/amazonUsSearch.html", "/amazonUsItem.html");
    }
}
