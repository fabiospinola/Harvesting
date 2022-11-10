package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.api.HarvestException;
import com.appdetex.harvest.api.MarketplaceDetection;
import com.appdetex.harvest.api.MarketplaceHarvester;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

public abstract class AbstractHarvester implements MarketplaceHarvester {
    private String baseurl;

    public AbstractHarvester(String baseurl) {
        this.baseurl = baseurl;
    }

    @Override
    public List<MarketplaceDetection> parseTarget(String searchTerm, String userAgent, int numItems) throws HarvestException {
        int pageOrder = 0;

        try {
            Document doc = Jsoup.connect(baseurl).userAgent(userAgent).get();
            System.out.printf("Title: %s\n\n\n\n", doc.title());
            return parseTarget(doc, userAgent, numItems);

        } catch (IOException e) {
            e.printStackTrace();
            throw new HarvestException();
        }
    }

    protected abstract List<MarketplaceDetection> parseTarget(Document doc, String userAgent, int numItems) throws HarvestException;

}
