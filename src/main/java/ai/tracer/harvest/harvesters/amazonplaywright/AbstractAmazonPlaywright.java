package ai.tracer.harvest.harvesters.amazonplaywright;

import ai.tracer.harvest.api.MarketplaceDetection;
import ai.tracer.harvest.api.MarketplaceHarvester;
import ai.tracer.harvest.harvesters.MarketplaceDetectionItem;

import ai.tracer.harvest.stopwatch.HarvesterAnalytics;
import ai.tracer.harvest.stopwatch.Stopwatch;
import ai.tracer.harvest.tracerclient.Requests;
import com.microsoft.playwright.*;

import java.io.IOException;
import java.util.*;

public abstract class AbstractAmazonPlaywright implements MarketplaceHarvester {

    private final String baseUrl;
    private final String marketplace;

    private HarvesterAnalytics harvesterAnalytics = new HarvesterAnalytics();

    private Requests requests = new Requests();

    private Stopwatch stopwatch = new Stopwatch();

    protected AbstractAmazonPlaywright(String baseUrl, String marketplace) {
        this.baseUrl = baseUrl;
        this.marketplace = marketplace;
    }

    @Override
    public List<MarketplaceDetection> parseTarget(String term, int numItems, Long customer_id) throws IOException {
        ArrayList<MarketplaceDetection> detections = new ArrayList<>();
        stopwatch.start();
        harvesterAnalytics.setHarvester(marketplace);
        harvesterAnalytics.setBrandTrack(term);
        try {

            Playwright playwright = Playwright.create();
            List<String> launchArgs = new ArrayList<>();
            launchArgs.add("--disable-dev-shm-usage");
            Browser playwrightBrowser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true).setSlowMo(5).setArgs(launchArgs));
            Page page = playwrightBrowser.newPage();
            page.setDefaultTimeout(5000);
            String baseURL = this.baseUrl + term;
            try {
                page.navigate(baseURL);
            }
            catch (Exception e){
                harvesterAnalytics.addConnectionFailure();
            }
            System.out.println(page.title());

            ArrayList<String> listTitles;
            ArrayList<String> listPrices;
            ArrayList<String> listUrl;
            ArrayList<String> listImgUrl;
            ArrayList<String> listSponsored;
            ArrayList<String> listDescriptions;
            ArrayList<ArrayList<String>> listInfo;

            Locator items = page.locator("xpath=//*[@id=\"search\"]/div[1]");
            items.waitFor();

            //HTML paths for each information
            Locator urls = items.locator("xpath=//a[@class=\"a-link-normal s-no-outline\"]");
            Locator imgUrls = items.locator("xpath=//img[@class=\"s-image\"]");
            Locator sponsored = items.locator("xpath=//div[@class=\"a-section a-spacing-base\"]");

            listImgUrl = extractImgUrls(imgUrls, numItems);

            listSponsored = extractSponsor(sponsored, numItems);

            listUrl = extractUrls(urls, numItems);

            listInfo = extractInfo(listUrl, page, numItems);

            listTitles = listInfo.get(0);
            listPrices = listInfo.get(1);
            listDescriptions = listInfo.get(2);

            int pageOrder = 0;
            for (int i = 0; i < numItems; i++) {
                pageOrder++;
                System.out.println("# " + pageOrder + " Detection");
                System.out.println(listTitles.get(i));
                System.out.println(listPrices.get(i));
                System.out.println(listSponsored.get(i));
                System.out.println(listUrl.get(i));
                System.out.println(listImgUrl.get(i));
                System.out.println(listDescriptions.get(i) + "\n");
                detections.add(new MarketplaceDetectionItem(listTitles.get(i), listDescriptions.get(i), listUrl.get(i), listImgUrl.get(i), pageOrder, listSponsored.get(i), listPrices.get(i), "open", "new", "Default", 1L));
            }
            playwrightBrowser.close();
        } catch (Exception e) {
            System.out.println("Playwright Exception");
        }
        harvesterAnalytics.setTime(stopwatch.getElapsedTime());
        requests.postHarvesterMetrics(harvesterAnalytics);
        System.out.println("Connection Failures captured: " + harvesterAnalytics.getConnectionFailure());
        System.out.println("Path Failures captured: " + harvesterAnalytics.getPathFailure());

        return detections;
    }

    private ArrayList<String> extractUrls(Locator locator, int numItems) {
        ArrayList<String> urlList = new ArrayList<>();
        for (int i = 0; i <= numItems; i++) {
            urlList.add(marketplace + locator.nth(i+1).getAttribute("href"));
        }
        return urlList;
    }

    private ArrayList<String> extractImgUrls(Locator locator, int numItems) {
        ArrayList<String> imgUrlList = new ArrayList<>();
        for (int i = 0; i <= numItems; i++) {
            imgUrlList.add(locator.nth(i+1).getAttribute("src"));
        }
        return imgUrlList;
    }

    private ArrayList<String> extractSponsor(Locator locator, int numItems) {
        ArrayList<String> sponsoredList = new ArrayList<>();
        switch (marketplace) {
            case "https://www.amazon.es":
                for (int i = 0; i <= numItems; i++) {
                    sponsoredList.add(String.valueOf(locator.nth(i+1).textContent().contains("Patrocinado")));
                }
                break;
            case "https://www.amazon.nl":
                for (int i = 0; i <= numItems; i++) {
                    sponsoredList.add(String.valueOf(locator.nth(i+1).textContent().contains("Sponsored")));
                }
        }
        return sponsoredList;
    }

    public ArrayList<ArrayList<String>> extractInfo(ArrayList<String> url, Page page, int numItems) {
        ArrayList<ArrayList<String>> infoList = new ArrayList<>();
        ArrayList<String> descriptionList = new ArrayList<>();
        ArrayList<String> titleList = new ArrayList<>();
        ArrayList<String> priceList = new ArrayList<>();
        try {
            Locator titlePath = page.locator("//title");
            Locator pricePath = page.locator("//div[@class=\"a-section a-spacing-none aok-align-center\"]/span/span | //*[@id=\"corePrice_desktop\"]/div/table/tbody/tr[1]/td[2]/span/span[1]" );
            Locator descPath = page.locator("ul.a-unordered-list.a-vertical.a-spacing-mini");//
            switch (marketplace) {
                case "https://www.amazon.es":
                    for (int i = 0; i < numItems; i++) {
                        try{
                            page.navigate(url.get(i));
                        }
                        catch (Exception e){
                            harvesterAnalytics.addConnectionFailure();
                        }
                        System.out.println(url.get(i));
                        titleList.add(titlePath.first().textContent());
                        try{
                            priceList.add(pricePath.first().textContent());
                        }
                        catch (Exception e){
                            harvesterAnalytics.addPathFailure();
                            System.out.println("Price Path not found");
                            priceList.add("0");
                        }
                        descriptionList.add(descPath.allInnerTexts().toString().replace("Acerca de este producto", " ")
                                .replace("› Ver más detalles", " "));
                    }
                    break;
                case "https://www.amazon.nl":
                    for (int i = 0; i < numItems; i++) {
                        try {
                            page.navigate(url.get(i));
                        }
                        catch(Exception e){
                            harvesterAnalytics.addConnectionFailure();
                        }
                        System.out.println(url.get(i));
                        titleList.add(titlePath.first().textContent());
                        try{
                            priceList.add(pricePath.first().textContent());
                        }
                        catch (Exception e){
                            harvesterAnalytics.addPathFailure();
                            System.out.println("Price Path not found");
                            priceList.add("0");
                        }
                        descriptionList.add(descPath.allInnerTexts().toString().replace("About this item", " "));
                    }
                    break;
            }
            infoList.add(titleList);
            infoList.add(priceList);
            infoList.add(descriptionList);
        } catch (Exception e) {
            System.out.println("Exception thrown");
            e.printStackTrace();
        }
        return infoList;
    }

    public List<MarketplaceDetection> parseTargetPlayTest(String htmlFile, int numItems, Long customer_id) {
        ArrayList<MarketplaceDetection> detections = new ArrayList<>();
        HarvesterAnalytics harvesterAnalytics = new HarvesterAnalytics();
        try {
            Playwright playwright = Playwright.create();
            List<String> launchArgs = new ArrayList<>();
            launchArgs.add("--disable-dev-shm-usage");
            Browser playwrightBrowser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true).setSlowMo(5).setArgs(launchArgs));
            Page page = playwrightBrowser.newPage();
            page.setDefaultTimeout(5000);
            //only for testing
            // String baseURL = ("file:///Users/fabio.spinola/Documents/WebScraping/Harvesting/WebScraper/src/test/resources/amazonEsSearch.html");
            page.navigate(htmlFile);
            //page.locator("xpath=//input[@class=\"a-button-input celwidget\"]").click();

            System.out.println(page.title());

            ArrayList<String> listTitles;
            ArrayList<String> listPrices;
            ArrayList<String> listUrl;
            ArrayList<String> listImgUrl;
            ArrayList<String> listSponsored;
            ArrayList<String> listDescriptions;
            ArrayList<ArrayList<String>> listInfo;

            Locator items = page.locator("xpath=//*[@id=\"search\"]/div[1]");
            items.waitFor();

            //HTML paths for each information
            Locator urls = items.locator("xpath=//a[@class=\"a-link-normal s-no-outline\"]");
            Locator imgUrls = items.locator("xpath=//img[@class=\"s-image\"]");
            Locator sponsored = items.locator("xpath=//div[@class=\"a-section a-spacing-base\"]");

            listImgUrl = extractImgUrls(imgUrls, numItems);

            listSponsored = extractSponsor(sponsored, numItems);

            listUrl = extractUrls(urls, numItems);

            listInfo = extractInfo(listUrl, page, numItems);

            listTitles = listInfo.get(0);
            listPrices = listInfo.get(1);
            listDescriptions = listInfo.get(2);

            int pageOrder = 0;
            for (int i = 0; i < numItems; i++) {
                pageOrder++;
                System.out.println("# " + pageOrder + " Detection");
                System.out.println(listTitles.get(i));
                System.out.println(listPrices.get(i));
                System.out.println(listSponsored.get(i));
                System.out.println(listUrl.get(i));
                System.out.println(listImgUrl.get(i));
                System.out.println(listDescriptions.get(i) + "\n");
                detections.add(new MarketplaceDetectionItem(listTitles.get(i), listDescriptions.get(i), listUrl.get(i), listImgUrl.get(i), pageOrder, listSponsored.get(i), listPrices.get(i), "open", "new", "Default", 1L));
            }
            playwrightBrowser.close();
        } catch (Exception e) {
            System.out.println("Exception thrown");
        }
        return detections;
    }
}
