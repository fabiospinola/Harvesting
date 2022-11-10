package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.api.MarketplaceDetection;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractEbayHarvester extends AbstractHarvester {

    EbayDynamicClass dynamic = new EbayDynamicClass();

    public AbstractEbayHarvester(String baseUrl) {
        super(baseUrl);
    }

    @Override
    protected List<HtmlElement> getElementsHtmlUnit(HtmlPage page) {
        return page.getByXPath("//ul[@class='srp-results srp-list clearfix']//li[contains(@class, 's-item s-item__pl-on-bottom')]//div[@class='s-item__wrapper clearfix']");
    }

    @Override
    protected MarketplaceDetection createDetectionHtmlUnit(HtmlElement src, int index, WebClient client) throws IOException {

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

        HtmlElement spanSponsored = src.getFirstByXPath(".//div[@class='s-item__details clearfix']//div[last()]//span//span");
        String classValue = dynamic.getClassValue(spanSponsored);
        String sponsor = classValue == null ? "No paid search information available for item" : classValue;

        LocalDateTime captureDate = LocalDateTime.now();

        System.out.println("\n" + index + "." +
                "\nDate: " + captureDate +
                "\nTitle: " + title +
                "\nPrice: " + price +
                "\nImage: " + imageUrl +
                "\nUrl: " + url +
                "\nDescription: " + description +
                "\nSponsored Class: " + sponsor +
                "\n");

        return new MarketplaceDetectionItem(captureDate, title, description, url, imageUrl, index, classValue, price);
    }
}
