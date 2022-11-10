package com.appdetex.harvest.marketplace;

public class AmazonUkHarvesterTest extends AbstractAmazonHarvesterTest {

    public AmazonUkHarvesterTest () {
        super(new AmazonUkHarvester(),"/amazonUkSearch.html", "amazonUkItem.html");
    }
}
