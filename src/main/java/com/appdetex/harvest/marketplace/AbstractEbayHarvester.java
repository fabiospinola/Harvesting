package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.api.HarvestException;
import com.appdetex.harvest.api.MarketplaceDetection;
import com.appdetex.harvest.api.MarketplaceHarvester;
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
    WebClient client = new WebClient();
    HtmlPage page;

    public AbstractEbayHarvester(String baseUrl) {
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
        HashMap<String, Integer> sponsoredClassNames = new HashMap<>();

        for (HtmlElement src : items) {
            parseSponsoredClassNameFromListing(src, sponsoredClassNames);
        }
        System.out.println("HASMAP: " + sponsoredClassNames);

        String sponsoredClassName = dynamic.countingClassTimes(sponsoredClassNames);
        int index = 0;
        for (HtmlElement src : items) {
            if (index == numItems) break;
            detections.add(createDetectionHtmlUnit(src, ++index, client, sponsoredClassName));
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

    protected MarketplaceDetection createDetectionHtmlUnit(HtmlElement src, int index, WebClient client, String sponsoredClassName) throws IOException {

        HtmlElement spanTitle = src.getFirstByXPath(".//div[@class='s-item__title']/span");
        String title = spanTitle == null ? "No title available for item" : ("\"" + spanTitle.asNormalizedText() + "\"");

        HtmlElement spanPrice = src.getFirstByXPath(".//div[@class='s-item__detail s-item__detail--primary']/span");
        String price = spanPrice == null ? "0.00" : ("\"" + spanPrice.asNormalizedText() + "\"");

        HtmlImage imgTag = src.getFirstByXPath(".//img");
        String imageUrl = imgTag == null ? "No image url available for item" : imgTag.getSrcAttribute();

        HtmlAnchor anchorUrl = src.getFirstByXPath(".//a");
        String url = anchorUrl == null ? "No item url available for item" : anchorUrl.getHrefAttribute();

        HtmlPage itemPage = client.getPage(url);
        HtmlElement spanDescription = itemPage.getFirstByXPath(".//div[@class='vim d-item-description']/iframe");
        String description = spanDescription == null ? "No description available for item for item" : ("\"" + spanDescription.asNormalizedText() + "\"");

        HtmlElement spanSponsored = src.getFirstByXPath(".//div[@class='s-item__details clearfix']//div[@class='s-item__detail s-item__detail--primary'][last()]//span//span");

        String classValue = dynamic.getClassValue(spanSponsored);
        String sponsor = classValue == null ? "No paid search information available for item" : classValue;

        String paid = Objects.equals(sponsor, sponsoredClassName) ? "true" : "false";


        System.out.println("\n" + index + "." +
                "\nTitle: " + title +
                "\nPrice: " + price +
                "\nImage: " + imageUrl +
                "\nUrl: " + url +
                "\nDescription: " + description +
                "\nSponsored Class: " + paid +
                "\nclassValue: " + classValue +
                "\n");

        return new MarketplaceDetectionItem(title, description, url, imageUrl, index, paid, price);
    }
}