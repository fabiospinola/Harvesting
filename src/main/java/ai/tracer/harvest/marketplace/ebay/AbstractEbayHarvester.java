package ai.tracer.harvest.marketplace.ebay;

import ai.tracer.harvest.api.HarvestException;
import ai.tracer.harvest.api.MarketplaceDetection;
import ai.tracer.harvest.api.MarketplaceHarvester;
import ai.tracer.harvest.marketplace.MarketplaceDetectionItem;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public abstract class AbstractEbayHarvester implements MarketplaceHarvester {

    EbayDynamicClass dynamic = new EbayDynamicClass();
    private String baseUrl;

    public AbstractEbayHarvester(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public List<MarketplaceDetection> parseTarget(String term, int numItems,Long customer_id) throws HarvestException {
        WebClient client = getWebClient();
        try {
            String baseUrl = this.baseUrl + URLEncoder.encode(term, StandardCharsets.UTF_8);
            //String baseUrl = this.baseUrl + term;
            HtmlPage page = client.getPage(baseUrl);
            return parseTargetInternalHtmlUnit(page, numItems, customer_id);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<MarketplaceDetection> parseTargetInternalHtmlUnit(HtmlPage page, int numItems,Long customer_id) throws Exception {
        ArrayList<MarketplaceDetection> detections = new ArrayList<>();
        List<HtmlElement> items = getElementsHtmlUnit(page);
        HashMap<String, Integer> sponsoredClassNames = new HashMap<>();

        for (HtmlElement src : items) {
            parseSponsoredClassNameFromListing(src, sponsoredClassNames);
        }
        System.out.println("HASMAP: " + sponsoredClassNames);

        String sponsoredClassName = dynamic.countingClassTimes(sponsoredClassNames);
        int index = 0;
        for (HtmlElement src : items) {
            if (index == numItems) break;
            detections.add(createDetectionHtmlUnit(src, ++index, sponsoredClassName, customer_id));
        }
        return detections;
    }

    protected List<HtmlElement> getElementsHtmlUnit(HtmlPage page) {
        return page.getByXPath("//ul[@class='srp-results srp-list clearfix']//li[contains(@class, 's-item s-item__pl-on-bottom')]//div[@class='s-item__wrapper clearfix']");
    }

    protected void parseSponsoredClassNameFromListing(HtmlElement src, HashMap<String, Integer> sponsoredClassNames) {
        HtmlElement spanSponsored = src.getFirstByXPath(".//div[@class='s-item__details clearfix']//div[@class='s-item__detail s-item__detail--primary'][last()]//span//span");
        String classValue = dynamic.getClassValue(spanSponsored);

        EbayDynamicClass.assignValueAndKey(sponsoredClassNames, classValue);
    }

    protected MarketplaceDetection createDetectionHtmlUnit(HtmlElement src, int index, String sponsoredClassName, Long customer_id) throws Exception {

        WebClient client = getWebClient();

        HtmlElement spanTitle = src.getFirstByXPath(".//div[@class='s-item__title']/span");
        String title = spanTitle == null ? "No title available for item" : ("\"" + spanTitle.asNormalizedText() + "\"");

        HtmlElement spanPrice = src.getFirstByXPath(".//div[@class='s-item__detail s-item__detail--primary']/span");
        String price = spanPrice == null ? "0.00" : ("\"" + spanPrice.asNormalizedText() + "\"");

        HtmlImage imgTag = src.getFirstByXPath(".//img");
        String imageUrl = imgTag == null ? "No image url available for item" : imgTag.getSrcAttribute();

        HtmlAnchor anchorUrl = src.getFirstByXPath(".//a");
        String url = anchorUrl == null ? "No item url available for item" : anchorUrl.getHrefAttribute();

        HtmlPage itemPage = client.getPage(url);
        HtmlElement spanDescription = itemPage.getFirstByXPath(".//div[@class='vim d-item-description']/iframe"); //
        String description = spanDescription == null ? "No description available for this item" : ("\"" + spanDescription.asNormalizedText() + "\"");

        HtmlElement spanSponsored = src.getFirstByXPath(".//div[@class='s-item__details clearfix']//div[@class='s-item__detail s-item__detail--primary'][last()]//span//span");

        String classValue = dynamic.getClassValue(spanSponsored);
        String sponsor = classValue == null ? "No paid search information available for item" : classValue;

        String paid = Objects.equals(sponsor, sponsoredClassName) ? "true" : "false";

        System.out.println("#" + index);
        System.out.println("Title:" + title);
        System.out.println("Price:" + price);
        System.out.println("Sponsored: " + paid);
        System.out.println("Description: " + description);
        System.out.println("URL: " + url);
        System.out.println("ImageURl: " + imageUrl);

        return new MarketplaceDetectionItem(title, description, url, imageUrl, index, paid, price,"open","new","Default",1L);
    }

    private static WebClient getWebClient() {
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setThrowExceptionOnFailingStatusCode(false);
        client.getOptions().setThrowExceptionOnScriptError(false);
        client.getOptions().setPrintContentOnFailingStatusCode(false);
        return client;
    }
}
