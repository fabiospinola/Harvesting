package ai.tracer.harvest.marketplace.amazonplaywright;

import ai.tracer.harvest.api.MarketplaceDetection;
import ai.tracer.harvest.api.MarketplaceHarvester;
import ai.tracer.harvest.marketplace.MarketplaceDetectionItem;
import com.microsoft.playwright.*;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAmazonEUHarvester implements MarketplaceHarvester {

    private String baseUrl;
    private String marketplace;

    protected AbstractAmazonEUHarvester(String baseUrl, String marketplace) {

        this.baseUrl = baseUrl;
        this.marketplace = marketplace;
    }

    @Override
    public List<MarketplaceDetection> parseTarget(String term, int numItems, Long customer_id) {
        ArrayList<MarketplaceDetection> detections = new ArrayList<>();
        try (Playwright playwright = Playwright.create()) {
            List<String> launchArgs = new ArrayList<>();
            launchArgs.add("--disable-dev-shm-usage");
            Browser playwrightBrowser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true).setSlowMo(5).setArgs(launchArgs));

            Page page = playwrightBrowser.newPage();

            String baseURL = this.baseUrl + term;
            page.navigate(baseURL);
            page.locator("xpath=//input[@class=\"a-button-input celwidget\"]").click();

            System.out.println(page.title());

            ArrayList<String> listTitles;
            ArrayList<String> listPrices;
            ArrayList<String> listUrl;
            ArrayList<String> listImgUrl;
            ArrayList<String> listSponsored;
            ArrayList<String> listDescription;

            Locator items = page.locator(".s-desktop-width-max .s-main-slot");
            items.waitFor();

            //HTML paths for each information
            Locator titles = items.locator("div.a-section.a-spacing-none span.a-size-base-plus.a-color-base.a-text-normal");
            Locator prices = items.locator("xpath=//span[@class=\"a-offscreen\"]");
            Locator urls = items.locator("xpath=//a[@class=\"a-link-normal s-no-outline\"]");
            Locator imgUrls = items.locator("xpath=//img[@class=\"s-image\"]");
            Locator sponsored = items.locator("xpath=//div[@class=\"a-section a-spacing-base\"]");


            listTitles = extractInfo(titles, numItems);

            listPrices = extractInfo(prices, numItems);

            listUrl = extractUrls(urls, numItems);

            listImgUrl = extractImgUrls(imgUrls, numItems);

            listSponsored = extractSponsor(sponsored, numItems);

            listDescription = extractDescriptions(listUrl,page,numItems);

            int pageOrder = 0;
            for (int i = 0; i < numItems; i++) {
                pageOrder++;
                System.out.println("# " + pageOrder + " Detection");
                System.out.println(listTitles.get(i));
                System.out.println(listPrices.get(i));
                System.out.println(listSponsored.get(i));
                System.out.println(listUrl.get(i));
                System.out.println(listImgUrl.get(i));
                System.out.println(listDescription.get(i) + "\n");
                detections.add(new MarketplaceDetectionItem(listTitles.get(i), listDescription.get(i), listUrl.get(i), listImgUrl.get(i), pageOrder, listSponsored.get(i), listPrices.get(i), "open", "new", "Default", 1L, customer_id));
            }
            playwrightBrowser.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return detections;
    }

    public List<MarketplaceDetection> parseTargetTest(String baseUrl, int numItems, Long customer_id) {
        ArrayList<MarketplaceDetection> detections = new ArrayList<>();
        try (Playwright playwright = Playwright.create()) {
            List<String> launchArgs = new ArrayList<>();
            launchArgs.add("--disable-dev-shm-usage");
            Browser playwrightBrowser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true).setSlowMo(5).setArgs(launchArgs));

            Page page = playwrightBrowser.newPage();

            //String baseURL = this.baseUrl + term;
            page.navigate(baseUrl);

            System.out.println(page.title());

            ArrayList<String> listTitles;
            ArrayList<String> listPrices;
            ArrayList<String> listUrl;
            ArrayList<String> listImgUrl;
            ArrayList<String> listSponsored;
            //ArrayList<String> listDescription;

            Locator items = page.locator(".s-desktop-width-max .s-main-slot");
            items.waitFor();

            //HTML paths for each information
            Locator titles = items.locator("div.a-section.a-spacing-none span.a-size-base-plus.a-color-base.a-text-normal");
            Locator prices = items.locator("xpath=//span[@class=\"a-offscreen\"]");
            Locator urls = items.locator("xpath=//a[@class=\"a-link-normal s-no-outline\"]");
            Locator imgUrls = items.locator("xpath=//img[@class=\"s-image\"]");
            Locator sponsored = items.locator("xpath=//div[@class=\"a-section a-spacing-base\"]");


            listTitles = extractInfo(titles, numItems);

            listPrices = extractInfo(prices, numItems);

            listUrl = extractUrls(urls, numItems);

            listImgUrl = extractImgUrls(imgUrls, numItems);

            listSponsored = extractSponsor(sponsored, numItems);

            //listDescription = extractDescriptions(listUrl,page,numItems);

            int pageOrder = 0;
            for (int i = 0; i < numItems; i++) {
                pageOrder++;
                System.out.println("# " + pageOrder + " Detection");
                System.out.println(listTitles.get(i));
                System.out.println(listPrices.get(i));
                System.out.println(listSponsored.get(i));
                System.out.println(listUrl.get(i));
                System.out.println(listImgUrl.get(i));
                //System.out.println(listDescription.get(i) + "\n");
                detections.add(new MarketplaceDetectionItem(listTitles.get(i), null, listUrl.get(i), listImgUrl.get(i), pageOrder, listSponsored.get(i), listPrices.get(i), "open", "new", "Default", 1L, customer_id));
            }
            playwrightBrowser.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return detections;
    }

    private ArrayList<String> extractInfo(Locator locator, int numItems) {
        ArrayList<String> infoList = new ArrayList<>();
        for (int i = 0; i < numItems; i++) {
            infoList.add(locator.nth(i).innerText());
        }
        return infoList;
    }

    private ArrayList<String> extractUrls(Locator locator, int numItems) {
        ArrayList<String> urlList = new ArrayList<>();
        for (int i = 0; i < numItems; i++) {
            urlList.add(marketplace + locator.nth(i).getAttribute("href"));
        }
        return urlList;
    }

    private ArrayList<String> extractImgUrls(Locator locator, int numItems) {
        ArrayList<String> imgUrlList = new ArrayList<>();
        for (int i = 0; i < numItems; i++) {
            imgUrlList.add(locator.nth(i).getAttribute("src"));
        }
        return imgUrlList;
    }

    private ArrayList<String> extractSponsor(Locator locator, int numItems) {
        ArrayList<String> sponsoredList = new ArrayList<>();
        switch (marketplace) {
            case "https://www.amazon.es":
                for (int i = 0; i < numItems; i++) {
                    sponsoredList.add(String.valueOf(locator.nth(i).textContent().contains("Patrocinado")));
                }
                break;
            case "https://www.amazon.nl":
                for (int i = 0; i < numItems; i++) {
                    sponsoredList.add(String.valueOf(locator.nth(i).textContent().contains("Sponsored")));
                }
        }
        return sponsoredList;
    }

    public ArrayList<String> extractDescriptions(ArrayList<String> url,Page page, int numItems) {
        ArrayList<String> descriptionList = new ArrayList<>();
        Locator descPath = page.locator("ul.a-unordered-list.a-vertical.a-spacing-mini");
        switch (marketplace) {
            case "https://www.amazon.es":
                for (int i = 0; i < numItems; i++) {
                    page.navigate(url.get(i));
                    descriptionList.add(descPath.allInnerTexts().toString().replace("Acerca de este producto", " ")
                            .replace("› Ver más detalles", " "));
                }
                break;
            case "https://www.amazon.nl":
                for (int i = 0; i < numItems; i++) {
                    page.navigate(url.get(i));
                    descriptionList.add(descPath.allInnerTexts().toString().replace("About this item", " "));
                }
                break;
            default:
                descriptionList.add(descPath.allInnerTexts().toString().replace("Acerca de este producto", " ")
                        .replace("› Ver más detalles", " "));
                break;

        }
        return descriptionList;
    }
}
