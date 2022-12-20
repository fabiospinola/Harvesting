package ai.tracer.harvest.marketplace;

import ai.tracer.harvest.api.MarketplaceDetection;
import ai.tracer.harvest.marketplace.amazonplaywright.AbstractAmazonEUHarvester;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

abstract class AbstractAmazonEUTest {
    private String htmlString;
    private AbstractAmazonEUHarvester harvester;
    private String htmlItem;

    public AbstractAmazonEUTest(AbstractAmazonEUHarvester harvester, String htmlString, String htmlItem){
        this.harvester = harvester;
        this.htmlString = htmlString;
        this.htmlItem = htmlItem;
    }

    @ParameterizedTest
    @ValueSource (ints = {0,1,2,3,5})
    public void testWithResults(int numResults){
        List<MarketplaceDetection> detections = null;
        try{
            detections = harvester.parseTarget(String.valueOf(htmlString),numResults,0L);
            Assertions.assertNotNull(detections);
            Assertions.assertTrue(detections.size() == numResults);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
