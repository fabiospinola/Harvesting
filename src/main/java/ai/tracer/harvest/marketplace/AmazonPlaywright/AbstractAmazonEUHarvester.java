package ai.tracer.harvest.marketplace.AmazonPlaywright;

import ai.tracer.harvest.api.MarketplaceDetection;
import ai.tracer.harvest.api.MarketplaceHarvester;
import ai.tracer.harvest.marketplace.MarketplaceDetectionItem;
import com.microsoft.playwright.*;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAmazonEUHarvester implements MarketplaceHarvester {

    private String baseUrl;

    protected AbstractAmazonEUHarvester(String baseUrl) {
        this.baseUrl = baseUrl;
    }
    @Override
    public List<MarketplaceDetection> parseTarget(String term, int numItems) {
        ArrayList<MarketplaceDetection> detections = new ArrayList<>();
        try (Playwright playwright = Playwright.create())
        {
            List<String> launchArgs = new ArrayList<>();
            launchArgs.add("--disable-dev-shm-usage");
            Browser playwrightBrowser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true).setSlowMo(5).setArgs(launchArgs));

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
            page.navigate(baseURL);
            page.locator("xpath=//input[@class=\"a-button-input celwidget\"]").click();

            System.out.println(page.title());

            ArrayList<String> listTitles;
            ArrayList<String> listPrices;
            ArrayList<String> listUrl = new ArrayList<>();
            ArrayList<String> listImgurl = new ArrayList<>();
            ArrayList<String> listSponsored = new ArrayList<>();
            ArrayList<String> listDescription = new ArrayList<>();

            Locator items = page.locator(".s-desktop-width-max .s-main-slot");
            items.waitFor();

            Locator titles = page.locator("div.a-section.a-spacing-none span.a-size-base-plus.a-color-base.a-text-normal");
            listTitles = (ArrayList<String>) titles.allInnerTexts();

            Locator prices = page.locator("xpath=//span[@class=\"a-offscreen\"]");
            listPrices = (ArrayList<String>) prices.allInnerTexts();
            for(int i = 0; i< numItems; i++){
                if(marketName.equals("ES")){
                    listSponsored.add(String.valueOf(page.locator("xpath=//div[@class=\"a-section a-spacing-base\"]").nth(i).innerText().contains("Patrocinado")));
                }
                else{
                    listSponsored.add(String.valueOf(page.locator("xpath=//div[@class=\"a-section a-spacing-base\"]").nth(i).innerText().contains("Sponsored")));
                }
                listUrl.add(marketUrl + items.locator("xpath=//a[@class=\"a-link-normal s-no-outline\"]").nth(i).getAttribute("href"));
                listImgurl.add(items.locator("xpath=//img[@class=\"s-image\"]").nth(i).getAttribute("src"));
            }

            for(int i = 0; i < numItems; i++) {
                page.navigate(listUrl.get(i));
                if (marketName.equals("ES")) {
                    Locator descPath = page.locator("ul.a-unordered-list.a-vertical.a-spacing-mini");
                    listDescription.add(descPath.allInnerTexts().toString().replace("Acerca de este producto", " ").replace("› Ver más detalles", " "));
                }
                else {
                    Locator descPath = page.locator("ul.a-unordered-list.a-vertical.a-spacing-mini");
                    listDescription.add(descPath.allInnerTexts().toString().replace("About this item", " "));
                }
            }

            int pageOrder = 0;
            for(int i = 0; i < numItems; i++){
                pageOrder++;
                System.out.println("# " + i + " Detection");
                System.out.println(listTitles.get(i));
                System.out.println(listPrices.get(i));
                System.out.println(listSponsored.get(i));
                System.out.println(listUrl.get(i));
                System.out.println(listImgurl.get(i));
                System.out.println(listDescription.get(i) + "\n");
                detections.add(new MarketplaceDetectionItem(listTitles.get(i),listDescription.get(i), listUrl.get(i),listImgurl.get(i),pageOrder,listSponsored.get(i),listPrices.get(i),"open","new","Default","Harvester"));
            }
            playwrightBrowser.close();
        }
        return detections;
    }
}
