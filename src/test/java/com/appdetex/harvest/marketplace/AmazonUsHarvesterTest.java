package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.marketplace.Amazon.AmazonUsHarvester;

public class AmazonUsHarvesterTest extends AbstractAmazonHarvesterTest {

    public AmazonUsHarvesterTest(){

        super (new AmazonUsHarvester() ,"/amazonUsSearch.html", "/amazonUsItem.html");
    }
}
