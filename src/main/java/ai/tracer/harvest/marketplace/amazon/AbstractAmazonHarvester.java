package ai.tracer.harvest.marketplace.amazon;

import ai.tracer.harvest.api.HarvestException;
import ai.tracer.harvest.api.MarketplaceDetection;
import ai.tracer.harvest.marketplace.MarketplaceDetectionItem;
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

    private String userAgent = "appdetex";


    public AbstractAmazonHarvester(String baseUrl) {

        super(baseUrl);
    }
    @Override
    protected List<MarketplaceDetection> parseTarget(Document doc, int numItems, Long customer_id) throws Exception {
        int pageOrder = 0;

        ArrayList<MarketplaceDetection> detections = new ArrayList<>();
        String marketplace = doc.title();
        String listingURL = null;
        String domain = doc.getElementsByClass("nav-logo-locale").text();
        Elements listing = doc.getElementsByClass("s-card-container s-overflow-hidden aok-relative puis-include-content-margin puis s-latency-cf-section s-card-border");

        for (int i = 0; i < numItems; i++) {

            pageOrder ++;

            String isPaidSearch = isPaidSearch(listing.get(i));

            String listingTitle = ("\"" + listing.get(i).getElementsByClass("a-size-medium a-color-base a-text-normal").text() + "\"");

            String listingPrice = (listing.get(i).getElementsByClass("a-price-whole").text() + listing.get(i).getElementsByClass("a-price-fraction").text());

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
            System.out.println("\t\tPrice: " + listingPrice + "$");
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
                    1L,
                    customer_id));
        }
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
        description = ("\"" + doc.getElementsByClass("a-section a-spacing-medium a-spacing-top-small").text().replace("About this item", " ").replace("About this item This fits your . Make sure this fits by entering your model number. ", "").replace("This fits your . Make sure this fits by entering your model number.", "").replace("  ", " ").replace("???","??F").replace("???","??C") + "\"");

        return description;
    }

    public String getDate (){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
}
