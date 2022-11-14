package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.marketplace.Amazon.AmazonUkHarvester;

public class AmazonUkHarvesterTest extends AbstractAmazonHarvesterTest {

    public AmazonUkHarvesterTest () {
        super(new AmazonUkHarvester(),"/amazonUkSearch.html", "/amazonUkItem.html");
    }
}
