package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.api.HarvestException;
import com.appdetex.harvest.api.MarketplaceDetection;
import com.appdetex.harvest.api.MarketplaceHarvester;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractHarvester implements MarketplaceHarvester {

    private String baseUrl;
    WebClient client = new WebClient();
    HtmlPage page;

    public AbstractHarvester(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public List<MarketplaceDetection> parseTarget(String term, int numItems) throws HarvestException {
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        try {
            String baseUrl = this.baseUrl + URLEncoder.encode(term, StandardCharsets.UTF_8);
            //String baseUrl = String.format(this.baseUrl, term);
            page = client.getPage(baseUrl);
            return parseTargetInternalHtmlUnit(page, numItems, client);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    protected List<MarketplaceDetection> parseTargetInternalHtmlUnit(HtmlPage page, int numItems, WebClient client) throws HarvestException, IOException {
        ArrayList<MarketplaceDetection> detections = new ArrayList<>();
        List<HtmlElement> items = getElementsHtmlUnit(page);
        int index = 0;
        for (HtmlElement src : items) {
            if (index == numItems) break;
            detections.add(createDetectionHtmlUnit(src, ++index, client));
        }
        return detections;
    }

    protected abstract List<HtmlElement> getElementsHtmlUnit(HtmlPage page);

    protected abstract MarketplaceDetection createDetectionHtmlUnit(HtmlElement src, int index, WebClient client) throws IOException;
}
