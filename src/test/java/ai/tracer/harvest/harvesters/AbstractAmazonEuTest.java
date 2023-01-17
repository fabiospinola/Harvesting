package ai.tracer.harvest.harvesters;

import ai.tracer.harvest.api.MarketplaceDetection;
import ai.tracer.harvest.harvesters.amazonplaywright.AbstractAmazonPlaywright;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

abstract class AbstractAmazonEuTest {
    private String pageWithResults;
    private AbstractAmazonPlaywright harvester;
    private String singleItem;

    public AbstractAmazonEuTest(AbstractAmazonPlaywright harvester, String pageWithResults, String singleItem){
        this.harvester = harvester;
        this.pageWithResults = "file://" + new File(pageWithResults).getAbsolutePath();
        this.singleItem = singleItem;
    }

    @ParameterizedTest
    @ValueSource(ints = {0,5})
    public void testWithResults(int numResults) {
        List<MarketplaceDetection> detections = null;
        try {
            detections = harvester.parseTargetPlayTest(pageWithResults,numResults,1L);
            Assertions.assertNotNull(detections);
            Assertions.assertTrue(detections.size() == numResults);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
