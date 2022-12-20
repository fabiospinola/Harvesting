package ai.tracer.harvest.marketplace;

import ai.tracer.harvest.api.MarketplaceDetection;
import ai.tracer.harvest.marketplace.amazonplaywright.AbstractAmazonEUHarvester;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

public abstract class AbstractAmazonEUHarvesterTest {
    private String pageWithResults;
    private AbstractAmazonEUHarvester harvester;
    private String pageWithoutResults;

    public AbstractAmazonEUHarvesterTest(AbstractAmazonEUHarvester harvester, String pageWithResults, String pageWithoutResults){
        this.harvester = harvester;
        this.pageWithResults = pageWithResults;
        this.pageWithoutResults = pageWithoutResults;
    }

    @ParameterizedTest
    @ValueSource (ints = {0,5})
    public void testWithResults(int numResults){
        List<MarketplaceDetection> detections = null;
        try{
            detections = harvester.parseTarget(pageWithResults,numResults,0L);
            Assertions.assertNotNull(detections);
            Assertions.assertTrue(detections.size() == numResults);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testWithoutResults(){
        List<MarketplaceDetection> detections = null;
        try{
            detections = harvester.parseTarget(pageWithoutResults,1,0L);
            Assertions.assertNotNull(detections);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
