package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.api.HarvestException;
import com.appdetex.harvest.api.MarketplaceDetection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

class AliexpressHarvesterTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 5, 10, 15})
    void parseTarget(int numResults) {
        AliexpressHarvester harvest = new AliexpressHarvester();
        List<MarketplaceDetection> detections = null;
        try {
            detections = harvest.parseAliexpress("jacuzzi", numResults);
            Assertions.assertNotNull(detections);
            Assertions.assertTrue(detections.size() == numResults, "Expecting " + (numResults+1));
        } catch (HarvestException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void parseStaticTarget(){
        AliexpressHarvester harvest = new AliexpressHarvester();
        List<MarketplaceDetection> detections = null;
        try{
            detections = harvest.parseStaticTarget("jacuzzi",2);
            Assertions.assertNotNull(detections);
            Assertions.assertTrue(detections.size() == 2, "Expecting 2 detections!");
            Assertions.assertTrue(detections.get(0).getTitle().equals("Product 1"));
            Assertions.assertTrue(detections.get(0).getPrice().equals("20,99€"));
            Assertions.assertTrue(detections.get(1).getTitle().equals("Product 2"));
            Assertions.assertTrue(detections.get(1).getPrice().equals("10,99€"));
        } catch (HarvestException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}