package ai.tracer.harvest.marketplace;

import ai.tracer.harvest.api.MarketplaceDetection;
import ai.tracer.harvest.marketplace.amazonplaywright.AbstractAmazonPlaywright;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
abstract class AbstractAmazonPlaywrightTest {
    private String pageWithResults;
    private AbstractAmazonPlaywright harvester;
    private String singleItem;

    public AbstractAmazonPlaywrightTest(AbstractAmazonPlaywright harvester, String pageWithResults, String singleItem){
        this.harvester = harvester;
        this.pageWithResults = pageWithResults;
        this.singleItem = singleItem;
    }

    @ParameterizedTest
    @ValueSource (ints = {0,1})
    public void testWithResults(int numResults) {
        List<MarketplaceDetection> detections = null;
        try {
            detections = harvester.parseTarget(pageWithResults, numResults, 0L);

            Assertions.assertNotNull(detections);
            Assertions.assertTrue(detections.size() == numResults);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}