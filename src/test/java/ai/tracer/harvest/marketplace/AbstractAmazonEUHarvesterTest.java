package ai.tracer.harvest.marketplace;

import ai.tracer.harvest.api.MarketplaceDetection;
import ai.tracer.harvest.marketplace.amazonplaywright.AbstractAmazonEUHarvester;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
abstract class AbstractAmazonEUHarvesterTest {
    private String pageWithResults;
    private AbstractAmazonEUHarvester harvester;
    private String singleItem;

    public AbstractAmazonEUHarvesterTest(AbstractAmazonEUHarvester harvester, String pageWithResults, String singleItem){
        this.harvester = harvester;
        this.pageWithResults = pageWithResults;
        this.singleItem = singleItem;
    }

    @ParameterizedTest
    @ValueSource (ints = {0,5})
    public void testWithResults(int numResults) {
        List<MarketplaceDetection> detections = null;
        try {
            detections = harvester.parseTargetTest(pageWithResults, numResults, 0L);
            Assertions.assertNotNull(detections);
            Assertions.assertTrue(detections.size() == numResults);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
