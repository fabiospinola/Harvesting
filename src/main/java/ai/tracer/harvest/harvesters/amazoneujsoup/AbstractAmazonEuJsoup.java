package ai.tracer.harvest.harvesters.amazoneujsoup;

import ai.tracer.harvest.api.HarvestException;
import ai.tracer.harvest.api.MarketplaceDetection;
import ai.tracer.harvest.api.MarketplaceHarvester;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

public abstract class AbstractAmazonEuJsoup implements MarketplaceHarvester {
    private final String baseurl;
    private final String userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:64.0) Gecko/20100101 Firefox/64.0";

    public AbstractAmazonEuJsoup(String baseurl){
        this.baseurl = baseurl;
    }
    @Override
    public List<MarketplaceDetection> parseTarget(String term, int numItems, Long customer_id) throws HarvestException, InterruptedException, IOException {
        String baseUrl = baseurl + term;
        try {
            Document doc = Jsoup.connect(baseUrl).userAgent(userAgent).get();
            System.out.printf("Title: %s\n", doc.title());
            return parseTarget(doc, numItems,customer_id);

        } catch (IOException e) {
            e.printStackTrace();
            throw new HarvestException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public abstract List<MarketplaceDetection> parseTarget(Document doc, int numItems, Long customer_id) throws Exception;
}
