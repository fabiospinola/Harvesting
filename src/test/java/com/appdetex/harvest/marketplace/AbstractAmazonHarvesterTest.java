package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.api.HarvestException;
import com.appdetex.harvest.api.MarketplaceDetection;
import com.appdetex.harvest.marketplace.Amazon.AbstractAmazonHarvester;
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

abstract class AbstractAmazonHarvesterTest {

    private String searchHtml;
    private String itemHtml;
    private AbstractAmazonHarvester harvester;

    public AbstractAmazonHarvesterTest(AbstractAmazonHarvester harvester, String searchHtml, String itemHtml) {
        this.harvester = harvester;
        this.searchHtml = searchHtml;
        this.itemHtml = itemHtml;
    }

    @ParameterizedTest
    @ValueSource (ints = {0, 5, 10, 20})
    public void testParseTarget(int numresults) {
        List<MarketplaceDetection> detections = null;
        try {
            Document doc = getHtml(searchHtml);
            detections = harvester.parseTarget(String.valueOf(doc), numresults);
            Assertions.assertNotNull(detections);
            Assertions.assertTrue(detections.size() ==numresults, "Expect " + numresults);
        } catch (HarvestException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetDescription(){
        String description = null;
        Document doc = getHtml(itemHtml);
        description = harvester.getDescription(doc);
        Assertions.assertNotNull(description);
    }

    private File getResourceHtml(String fileName){
        URL url  = this.getClass().getResource(fileName);
        try {
            return new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private Document getHtml(String fileName) {
        File html = getResourceHtml(fileName);
        try {
            return Jsoup.parse(html);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}