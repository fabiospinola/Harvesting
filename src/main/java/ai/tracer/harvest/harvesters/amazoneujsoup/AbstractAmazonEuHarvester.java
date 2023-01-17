package ai.tracer.harvest.harvesters.amazoneujsoup;

import ai.tracer.harvest.api.HarvestException;
import ai.tracer.harvest.api.MarketplaceDetection;
import ai.tracer.harvest.harvesters.MarketplaceDetectionItem;
import ai.tracer.harvest.stopwatch.HarvesterAnalytics;
import ai.tracer.harvest.stopwatch.Stopwatch;
import ai.tracer.harvest.tracerclient.TracerClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAmazonEuHarvester extends AbstractAmazonEuJsoup {

    private final String userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:64.0) Gecko/20100101 Firefox/64.0";

    private HarvesterAnalytics harvesterAnalytics = new HarvesterAnalytics();

    private TracerClient tracerClient = new TracerClient();

    private Stopwatch stopwatch = new Stopwatch();

    public AbstractAmazonEuHarvester(String baseurl) {
        super(baseurl);
    }

    @Override
    public List<MarketplaceDetection> parseTarget(Document doc, int numItems, Long customer_id) throws Exception {
        ArrayList<MarketplaceDetection> detections = new ArrayList<>();
        stopwatch.start();
        String marketPlaceTitle = doc.title();
        String brandTrack = marketPlaceTitle.substring(marketPlaceTitle.indexOf(":") + 1);
        String harvester = marketPlaceTitle.substring(0,marketPlaceTitle.indexOf(":"));

        brandTrack.trim();
        harvester.trim();
        harvesterAnalytics.setHarvester(harvester);
        harvesterAnalytics.setBrandTrack(brandTrack);
        String marketplace;
        int pageorder = 0;
        if (marketPlaceTitle.contains("Amazon.es")){
            marketplace = "https://www.amazon.es";
        }
        else{
            marketplace = "https://www.amazon.nl/-/en";
        }
        System.out.println(marketPlaceTitle);
        Elements listings = doc.getElementsByClass("a-section a-spacing-base");
        for(int i = 0; i < numItems;i++) {
            pageorder++;
            String title = listings.get(i).getElementsByClass("a-size-mini a-spacing-none a-color-base s-line-clamp-4").text();
            String price = null;
            try {
                price = listings.get(i).getElementsByClass("a-offscreen").text();
            } catch (Exception e) {
                harvesterAnalytics.addPathFailure();
            }
            String sponsored = String.valueOf(isSponsored(listings.get(i).getElementsByClass("s-label-popover-default")));
            String url = listings.get(i).getElementsByClass("a-link-normal s-no-outline").attr("href");
            String itemUrl = marketplace + url;
            String imgUrl = listings.get(i).getElementsByClass("s-image").attr("src");
            String description = getDescription(itemUrl, userAgent);

            System.out.println("#Page order: " + pageorder);
            System.out.println(title);
            System.out.println(price);
            System.out.println(sponsored);
            System.out.println(itemUrl);
            System.out.println(imgUrl);
            System.out.println(description);
            System.out.println("State: open");
            System.out.println("Status: new");
            System.out.println("Reason: Default");
            System.out.println("Analyst: " + 1L);
            System.out.println("\n");

            detections.add(new MarketplaceDetectionItem(title, description, itemUrl, imgUrl, pageorder, sponsored, price, "open", "new", "Default", 1L));
        }
        stopwatch.stop();
        harvesterAnalytics.setTime(stopwatch.getElapsedTime());
        tracerClient.postHarvesterMetrics(harvesterAnalytics);
        return detections;
    }

    public List<MarketplaceDetection> parseTargetTest(Document doc, int numItems, Long customer_id) throws Exception {
        ArrayList<MarketplaceDetection> detections = new ArrayList<>();
        String marketPlaceTitle = doc.title();

        String marketplace;
        int pageorder = 0;
        if (marketPlaceTitle.contains("Amazon.es")){
            marketplace = "https://www.amazon.es";
        }
        else{
            marketplace = "https://www.amazon.nl/-/en";
        }
        System.out.println(marketPlaceTitle);
        Elements listings = doc.getElementsByClass("a-section a-spacing-base");
        for(int i = 0; i < numItems;i++) {
            pageorder++;
            String title = listings.get(i).getElementsByClass("a-size-mini a-spacing-none a-color-base s-line-clamp-4").text();
            String price = null;
            try {
                price = listings.get(i).getElementsByClass("a-offscreen").text();
            } catch (Exception e) {
            }
            String sponsored = String.valueOf(isSponsored(listings.get(i).getElementsByClass("s-label-popover-default")));
            String url = listings.get(i).getElementsByClass("a-link-normal s-no-outline").attr("href");
            String itemUrl = marketplace + url;
            String imgUrl = listings.get(i).getElementsByClass("s-image").attr("src");
            String description = getDescription(itemUrl, userAgent);

            System.out.println("#Page order: " + pageorder);
            System.out.println(title);
            System.out.println(price);
            System.out.println(sponsored);
            System.out.println(itemUrl);
            System.out.println(imgUrl);
            System.out.println(description);
            System.out.println("State: open");
            System.out.println("Status: new");
            System.out.println("Reason: Default");
            System.out.println("Analyst: " + 1L);
            System.out.println("\n");

            detections.add(new MarketplaceDetectionItem(title, description, itemUrl, imgUrl, pageorder, sponsored, price, "open", "new", "Default", 1L));
        }
        return detections;
    }
    private boolean isSponsored(Elements elements){
        if(elements.text().isEmpty() || elements.text().isBlank()){
            return false;
        }
        else return true;
    }

    private String getDescription(String itemUrl, String userAgent) throws HarvestException {
        Document productPage = null;
        try {
            productPage = Jsoup.connect(itemUrl).userAgent(userAgent).get();

        } catch (IOException e) {
            e.printStackTrace();
            throw new HarvestException();
        }

        return getDescription(productPage);
    }

    public String getDescription(Document doc){
        String description = null;
        description = ("\"" + doc.getElementsByClass("a-unordered-list a-vertical a-spacing-mini").text() + "\"");
        return description;
    }
}
