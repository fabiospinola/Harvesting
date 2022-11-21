package com.appdetex.harvest.marketplace.AmazonPlaywright;

import com.appdetex.harvest.api.HarvestException;
import com.appdetex.harvest.api.MarketplaceDetection;
import com.appdetex.harvest.api.MarketplaceHarvester;
import com.appdetex.harvest.marketplace.MarketplaceDetectionItem;
import com.microsoft.playwright.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAmazonEUHarvester implements MarketplaceHarvester {

    private String baseUrl;

    public AbstractAmazonEUHarvester(String baseUrl) {
        this.baseUrl = baseUrl;
    }
    @Override
    public List<MarketplaceDetection> parseTarget(String term, int numItems) throws HarvestException, InterruptedException, IOException {
        ArrayList<MarketplaceDetection> detections = new ArrayList<>();
        try (Playwright playwright = Playwright.create())
        {
            BrowserType webkit = playwright.webkit();
            List<String> launchArgs = new ArrayList<>();
            launchArgs.add("--disable-dev-shm-usage");
            Browser playwrightBrowser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true).setSlowMo(5).setArgs(launchArgs));

            //Browser browser = webkit.launch();
            //BrowserContext context = browser.newContext(new Browser.NewContextOptions().setUserAgent("cantgetme"));
            Page page = playwrightBrowser.newPage();
            String marketUrl;
            String marketName;
            if (this.baseUrl == "https://www.amazon.nl/-/en/s?k="){
                marketUrl = "https://www.amazon.nl";
                marketName = "NL";
            }
            else {
                marketUrl = "https://www.amazon.es";
                marketName = "ES";
            }

            String baseURL = this.baseUrl + term;
            //String baseUrl = "https://www.amazon.nl/s?k=" + term + "&language=en_GB&__mk_nl_NL=%C3%85M%C3%85%C5%BD%C3%95%C3%91&crid=1BSQVNAM8DXI1&sprefix=" + term + "%2Caps%2C97&ref=nb_sb_noss_1";
            //"https://www.amazon.nl/s?k=" + term + "&language=en_GB&__mk_nl_NL=%C3%85M%C3%85%C5%BD%C3%95%C3%91&crid=1BSQVNAM8DXI1&sprefix=" + term + "%2Caps%2C97&ref=nb_sb_noss_1"
            page.navigate(baseURL);
            page.locator("xpath=//input[@class=\"a-button-input celwidget\"]").click();
            if(marketName == "ES"){
                page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("AmazonES.png")).setFullPage(true));
            }
            else{
                page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("AmazonNL.png")).setFullPage(true));
            }

            System.out.println(page.title());

            ArrayList<String> listTitles;
            ArrayList<String> listPrices;
            ArrayList<String> listUrl = new ArrayList<>();
            ArrayList<String> listImgurl = new ArrayList<>();
            ArrayList<String> listSponsored = new ArrayList<>();
            ArrayList<String> listDescription = new ArrayList<>();

            ArrayList<Locator> itemsList = new ArrayList<>();

            Locator items = page.locator(".s-desktop-width-max .s-main-slot");
            //Locator items = page.locator("div.s-main-slot.s-result-list.s-search-results.sg-row");
            items.waitFor();

            Locator titles = page.locator("div.a-section.a-spacing-none span.a-size-base-plus.a-color-base.a-text-normal");
            listTitles = (ArrayList<String>) titles.allInnerTexts();

            Locator prices = page.locator("span.a-price span.a-price-whole");
            listPrices = (ArrayList<String>) prices.allInnerTexts();
            for(int i = 0; i<numItems; i++){
                if(marketName == "ES"){
                    //Thread.sleep(1000);
                    listSponsored.add(String.valueOf(page.locator("xpath=//div[@class=\"a-section a-spacing-base\"]").nth(i).innerText().contains("Patrocinado")));
                    //Thread.sleep(1000);
                }
                else{
                    //Thread.sleep(1000);
                    listSponsored.add(String.valueOf(page.locator("xpath=//div[@class=\"a-section a-spacing-base\"]").nth(i).innerText().contains("Sponsored")));
                    //Thread.sleep(1000);
                }
                listUrl.add(marketUrl + items.locator("xpath=//a[@class=\"a-link-normal s-no-outline\"]").nth(i).getAttribute("href"));
                listImgurl.add(items.locator("xpath=//img[@class=\"s-image\"]").nth(i).getAttribute("src"));
            }

            for(int i = 0; i < numItems; i++){
                page.navigate(listUrl.get(i));
                listDescription.add(page.locator("xpath=//div[@class=\"a-section a-spacing-medium a-spacing-top-small\"]").textContent().isEmpty() ? "No Description Available" : page.locator("xpath=//div[@class=\"a-section a-spacing-medium a-spacing-top-small\"]").textContent());
            }

            for(int i = 0; i<numItems; i++){
                System.out.println("# " + i + " Detection");
                System.out.println(listTitles.get(i));
                System.out.println(listPrices.get(i));
                System.out.println(listSponsored.get(i));
                System.out.println(listUrl.get(i));
                System.out.println(listImgurl.get(i));
                System.out.println(listDescription.get(i) + "\n");
                detections.add(new MarketplaceDetectionItem(listTitles.get(i),listDescription.get(i), listUrl.get(i),listImgurl.get(i),(i+1),listSponsored.get(i),listPrices.get(i)));
            }
            playwrightBrowser.close();
        }
        return detections;
    }
}
