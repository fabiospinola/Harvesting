package org.example;

import com.appdetex.harvest.api.HarvestException;
import com.appdetex.harvest.marketplace.EbayUkHarvester;
import com.appdetex.harvest.marketplace.EbayUsHarvester;

public class Main {
    public static void main(String[] args) throws HarvestException {

        System.out.println("Hello world!");

        new EbayUkHarvester().parseTarget("Jacuzzi", 10);
        new EbayUsHarvester().parseTarget("Jacuzzi", 20);

        System.out.println("Goodbye world!");

    }
}