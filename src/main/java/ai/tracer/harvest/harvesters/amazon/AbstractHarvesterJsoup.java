package ai.tracer.harvest.harvesters.amazon;

import ai.tracer.harvest.api.HarvestException;
import ai.tracer.harvest.api.MarketplaceDetection;
import ai.tracer.harvest.api.MarketplaceHarvester;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

public abstract class AbstractHarvesterJsoup implements MarketplaceHarvester {
    private String baseurl;

    private String userAgent = "appdetex40";

    public AbstractHarvesterJsoup(String baseurl) {
        this.baseurl = baseurl;
    }

    @Override
    public List<MarketplaceDetection> parseTarget(String searchTerm, int numItems, Long customer_id) throws HarvestException {
        int pageOrder = 0;

        //this.baseurl = baseurl + searchTerm;
        String baseUrl = baseurl + searchTerm;
        try {
            Document doc = Jsoup.connect(baseUrl).userAgent(userAgent).get();
            System.out.printf("Title: %s\n\n\n\n", doc.title());
            return parseTarget(doc, numItems,customer_id);

        } catch (IOException e) {
            e.printStackTrace();
            throw new HarvestException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<MarketplaceDetection> parseTargetTest(String searchTerm, int numItems,Long customer_id) throws Exception {
        int pageOrder = 0;

        //this.baseurl = baseurl + searchTerm;

        Document doc = Jsoup.parse(searchTerm);
        System.out.printf("Title: %s\n\n\n\n", doc.title());
        return parseTargetTestTesting(doc, numItems,customer_id);

    }

    protected abstract List<MarketplaceDetection> parseTargetTestTesting(Document doc, int numItems, Long customer_id);

    protected abstract List<MarketplaceDetection> parseTarget(Document doc, int numItems, Long customer_id) throws Exception;
}
