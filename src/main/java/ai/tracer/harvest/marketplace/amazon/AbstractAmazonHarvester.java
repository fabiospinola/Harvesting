package ai.tracer.harvest.marketplace.amazon;

import ai.tracer.harvest.api.HarvestException;
import ai.tracer.harvest.api.MarketplaceDetection;
import ai.tracer.harvest.marketplace.MarketplaceDetectionItem;
import ai.tracer.harvest.stopwatch.HarvesterAnalytics;
import ai.tracer.harvest.stopwatch.Stopwatch;
import ai.tracer.harvest.tracerclient.Requests;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class AbstractAmazonHarvester extends AbstractHarvesterJsoup {

    private final String userAgent = "appdetex";

    private HarvesterAnalytics harvesterAnalytics = new HarvesterAnalytics();

    private Requests requests = new Requests();

    private Stopwatch stopwatch = new Stopwatch();

    public AbstractAmazonHarvester(String baseUrl) {

        super(baseUrl);
    }
    @Override
    protected List<MarketplaceDetection> parseTarget(Document doc, int numItems, Long customer_id) throws Exception {
        int pageOrder = 0;
        boolean isGalleryView = false;
        stopwatch.start();
        ArrayList<MarketplaceDetection> detections = new ArrayList<>();
        String listingURL = null;
        String domain = doc.getElementsByClass("nav-logo-locale").text();
        String marketPlaceTitle = doc.title();
        String brandTrack = marketPlaceTitle.substring(marketPlaceTitle.indexOf(":") + 1);
        String harvester = marketPlaceTitle.substring(0,marketPlaceTitle.indexOf(":"));

        brandTrack.trim();
        harvester.trim();
        harvesterAnalytics.setHarvester(harvester);
        harvesterAnalytics.setBrandTrack(brandTrack);
        Elements listing = doc.getElementsByClass("s-card-container s-overflow-hidden aok-relative puis-include-content-margin puis s-latency-cf-section s-card-border");
        String listingTitle;
        String listingPrice = null;

        //when the page is in gallery view the class names are different
        if(listing.size() == 0){
            listing = doc.getElementsByClass("s-card-container s-overflow-hidden aok-relative puis-expand-height puis-include-content-margin puis s-latency-cf-section s-card-border");
            isGalleryView = true;
        }
        if(listing.size() < numItems){ //in case the number of listings is less than the number of items wanted
            numItems = listing.size();
        }
        for (int i = 0; i < numItems; i++) {

            pageOrder ++;

            String isPaidSearch = isPaidSearch(listing.get(i));

            if(isGalleryView) {
                listingTitle = ("\"" + listing.get(i).getElementsByClass("a-size-base-plus a-color-base a-text-normal").text() + "\"");
            }
            else{
                listingTitle = ("\"" + listing.get(i).getElementsByClass("a-size-medium a-color-base a-text-normal").text() + "\"");
            }
            try {
                listingPrice = (listing.get(i).getElementsByClass("a-price-whole").text() + listing.get(i).getElementsByClass("a-price-fraction").text() + listing.get(i).getElementsByClass("a-price-symbol").text());
            } catch(Exception e){
                harvesterAnalytics.addPathFailure();
            }
            //String listingPrice = (listing.get(i).getElementsByClass("a-offscreen").text());
            String imageUrl = listing.get(i).getElementsByClass("s-image").attr("src");

            if (domain.equals(".us")) {
                listingURL = ("https://www.amazon.com" + listing.get(i).getElementsByClass("a-link-normal s-no-outline").attr("href"));
            }
            else {
                listingURL = ("https://www.amazon" + domain + listing.get(i).getElementsByClass("a-link-normal s-no-outline").attr("href"));
            }
            String description = getDescription(listingURL, userAgent);
            String detectionState = "new";
            String detectionStatus = "open";
            String detectionReason = "Default";
            String detectionAnalyst = "Harvester";

            System.out.println("#" + pageOrder + " - " + listingTitle + " - " + listingURL);
            System.out.println("\t\t" + imageUrl);
            System.out.println("\t\tSponsored: " + isPaidSearch);
            System.out.println("\t\tPrice: " + listingPrice);
            System.out.println("\t\tDescription: " + description);
            System.out.println("\t\tState: " + detectionState);
            System.out.println("\t\tStatus: " + detectionStatus);
            System.out.println("\t\tReason: " + detectionReason);
            System.out.println("\t\tAnalyst: " + detectionAnalyst);
            System.out.println();
            System.out.println("\n");

            detections.add(new MarketplaceDetectionItem(listingTitle,
                    description,
                    listingURL,
                    imageUrl,
                    pageOrder,
                    isPaidSearch,
                    listingPrice,
                    "open",
                    "new",
                    "Default",
                    1L));
        }
        stopwatch.stop();
        harvesterAnalytics.setTime(stopwatch.getElapsedTime());
        requests.postHarvesterMetrics(harvesterAnalytics);
        return detections;
    }

    public void harvest(String searchTerm) throws IOException {

        String url = getSearchUrl(searchTerm);

        System.out.println(url);
        System.out.println("Starting harvesting...(you should feel like a hacker)\n\n");

    }

    public String getSearchUrl(String searchTerm) {
        if (searchTerm.contains(" ")) {
            searchTerm = searchTerm.replace(" ", "+");
        }

        String url = "https://www.amazon.com/s?k=" + searchTerm + "&crid=FQLYPINM8486&sprefix=" + searchTerm + "%2Caps%2C297&ref=nb_sb_noss_1";
        return url;
    }

    public String isPaidSearch(Element productEntry) {
        String isPaidSearch = "false";

        if (productEntry.getElementsByClass("a-color-secondary").text().contains("Sponsored")) {
            isPaidSearch = "true";
        }
        return isPaidSearch;
    }

    public String getDescription(String productUrl, String userAgent) throws HarvestException {
        String description = null;
        Document productPage = null;

        try {
            productPage = Jsoup.connect(productUrl).userAgent(userAgent).get();

        } catch (IOException e) {
            e.printStackTrace();
            throw new HarvestException();
        }

        return getDescription(productPage);
    }

    public String getDescription(Document doc){
        String description = null;
        description = ("\"" + doc.getElementsByClass("a-section a-spacing-medium a-spacing-top-small").text().replace("About this item", " ").replace("About this item This fits your . Make sure this fits by entering your model number. ", "").replace("This fits your . Make sure this fits by entering your model number.", "").replace("  ", " ").replace("℉","ºF").replace("℃","ºC") + "\"");

        return description;
    }

    public String getDate (){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
}
