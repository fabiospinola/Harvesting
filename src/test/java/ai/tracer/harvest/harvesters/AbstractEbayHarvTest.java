package ai.tracer.harvest.harvesters;

import ai.tracer.harvest.api.MarketplaceDetection;
import ai.tracer.harvest.harvesters.ebay.AbstractEbayHarvester;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.List;

public abstract class AbstractEbayHarvTest {

    private String repleteResultsHTml;
    private String emptyResultsHtml;
    private AbstractEbayHarvester abstractEbayHarvester;

    public AbstractEbayHarvTest(String repleteResultsHTml, String emptyResultsHtml, AbstractEbayHarvester abstractEbayHarvester) {
        this.repleteResultsHTml = repleteResultsHTml;
        this.emptyResultsHtml = emptyResultsHtml;
        this.abstractEbayHarvester = abstractEbayHarvester;
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 5})
    public void testWithResults(int numResults) {
        List<MarketplaceDetection> detections = null;
        try {
            HtmlPage page = getHtmlPage(repleteResultsHTml);
            detections = abstractEbayHarvester.parseTargetInternalHtmlUnitTest(page, numResults,0L);
            Assertions.assertNotNull(detections);
            Assertions.assertTrue(detections.size() == numResults);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testEmptyResultsHtml() {
        List<MarketplaceDetection> detections = null;

        try {
            HtmlPage htmlPage = getHtmlPage(emptyResultsHtml);
            detections = abstractEbayHarvester.parseTargetInternalHtmlUnitTest(htmlPage, 10,0L);
            Assertions.assertNotNull(detections);
            Assertions.assertTrue(detections.size() == 0, "No items expected");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private HtmlPage getHtmlPage(String fileName) throws IOException {
        WebClient client = new WebClient();
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setCssEnabled(false);
        return client.getPage(fileName);
    }
}
