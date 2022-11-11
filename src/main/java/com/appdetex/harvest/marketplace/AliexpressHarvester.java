package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.api.HarvestException;
import com.appdetex.harvest.api.MarketplaceDetection;
import com.appdetex.harvest.api.MarketplaceHarvester;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AliexpressHarvester implements MarketplaceHarvester {

    public static ArrayList<String> Titles = new ArrayList<>();
    public static ArrayList<String> Prices = new ArrayList<>();
    public static ArrayList<String> Images = new ArrayList<>();
    public static ArrayList<String> Urls = new ArrayList<>();
    public static ArrayList<String> Sponsored = new ArrayList<>();
    public static ArrayList<String> Descriptions = new ArrayList<>();


    @Override
    public List<MarketplaceDetection> parseTarget(String term, int numItems) throws HarvestException, InterruptedException {
        ArrayList<MarketplaceDetection> detections = new ArrayList<>();
        WebDriver driver = getWebDriver();

        String URL = "https://pt.aliexpress.com/wholesale?trafficChannel=main&d=y&CatId=0&SearchText=" + term + "&ltype=wholesale&SortType=default&g=y";
        //https://pt.aliexpress.com/wholesale?trafficChannel=main&d=y&CatId=0&SearchText=jacuzzi&ltype=wholesale&SortType=default&g=y
        driver.get(URL);
        driver.manage().window().maximize();
        Thread.sleep(2000);
        JavascriptExecutor jse = getJavascriptExecutor((JavascriptExecutor) driver); //script to do some scrolling on the page

        WebElement pageContent = driver.findElement(By.cssSelector(".JIIxO"));                  //find the products container
        List<WebElement> itemTitle = pageContent.findElements(By.cssSelector("._18_85")); //find and list the all the Titles in the products container
        List<WebElement> itemPrice = pageContent.findElements(By.cssSelector(".mGXnE._37W_B")); //find and list the all the Prices in the products container
        List<WebElement> itemImgURL = pageContent.findElements(By.cssSelector("._1RtJV.product-img"));      //find and list the all the Image's URLs in the products container
        List<WebElement> itemURL = pageContent.findElements(By.cssSelector("._3t7zg._2f4Ho"));  //find and list the all the Items URLs in the products container
        List<WebElement> itemAd = pageContent.findElements(By.cssSelector("._3A0hz.gYJvK"));    //find and list the all the Sponsored marks in the products container

        for (int i = 0; i < numItems; i++) {
            Titles.add(itemTitle.get(i).getText());
            Prices.add(itemPrice.get(i).getText());
            Sponsored.add(itemAd.get(i).getText().isEmpty() ? "false" : "true");
            Images.add(itemImgURL.get(i).getAttribute("src"));
            Urls.add(itemURL.get(i).getAttribute("href"));
        }

        getDescriptions(driver, jse, numItems);
        for (int i = 0; i < numItems; i++){
            detections.add(new MarketplaceDetectionItem(Titles.get(i), Descriptions.get(i), Urls.get(i), Images.get(i),(i + 1), Sponsored.get(i), Prices.get(i)));
            System.out.println("Detection #:" + (i + 1));
            System.out.println("Title: " + Titles.get(i));
            System.out.println("Url: " + Urls.get(i));
            System.out.println("Image: " + Images.get(i));
            System.out.println("Is paid: " + Sponsored.get(i));
            System.out.println("Price: " + Prices.get(i));
            System.out.println("Description: " + Descriptions.get(i));
            System.out.println("--");
        }
        driver.close();
        driver.quit();
        return detections;
    }


    public List<MarketplaceDetection> parseStaticTarget(String term, int numItems) throws HarvestException, InterruptedException {
        ArrayList<MarketplaceDetection> detections = new ArrayList<>();
        WebDriver driver = getWebDriver();

        driver.manage().window().maximize();

        //String searchTerm = term;
        Path sampleFile = Paths.get("src/test/resources/mock-page.html");
        driver.get(sampleFile.toUri().toString());

        driver.get(sampleFile.toUri().toString());
        Thread.sleep(2000);
        JavascriptExecutor jse = getJavascriptExecutor((JavascriptExecutor) driver); //script to do some scrolling on the page

        WebElement pageContent = driver.findElement(By.cssSelector(".JIIxO"));                  //find the products container
        List<WebElement> itemTitle = pageContent.findElements(By.cssSelector("._18_85")); //find and list the all the Titles in the products container
        List<WebElement> itemPrice = pageContent.findElements(By.cssSelector(".mGXnE._37W_B")); //find and list the all the Prices in the products container

        for (int i = 0; i < numItems; i++) {
            Titles.add(itemTitle.get(i).getText());
            Prices.add(itemPrice.get(i).getText());
        }
        for (int i = 0; i < numItems; i++) {
            detections.add(new MarketplaceDetectionItem(Titles.get(i),"Description", "URL", "ImgURL",(i + 1), "Sponsored", Prices.get(i)));
        }
            driver.close();
        driver.quit();
        return detections;
    }

    private static WebDriver getWebDriver() {
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver"); //src/main/resources/chromedriver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("user-data-dir=/tmp/temp_profile");
        options.addArguments("start-maximized"); // open Browser in maximized mode
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        options.addArguments("--headless");
        return new ChromeDriver(options);
    }

    private static void getDescriptions(WebDriver driver, JavascriptExecutor jse, int numItems) throws InterruptedException {
        for (int i = 0; i < numItems; i++) {
            driver.navigate().to(Urls.get(i));
            jse.executeScript("scrollBy(0,500)");
            jse.executeScript("scrollBy(0,500)");
            Thread.sleep(2000);
            Descriptions.add(driver.findElement(By.cssSelector(".product-overview")).getText().isEmpty() ? "\"Description not available\"" : "\"" + driver.findElement(By.cssSelector(".product-overview")).getText().replace("<br>" , " ").replace("::marker" , " ").replace("\"", " ") + "\"");
        }
    }

    private static JavascriptExecutor getJavascriptExecutor(JavascriptExecutor driver) {
        driver.executeScript("scrollBy(0,500)");
        driver.executeScript("scrollBy(0,500)");
        driver.executeScript("scrollBy(0,500)");
        driver.executeScript("scrollBy(0,500)");
        driver.executeScript("scrollBy(0,500)");
        driver.executeScript("scrollBy(0,500)");
        driver.executeScript("scrollBy(0,500)");
        driver.executeScript("scrollBy(0,500)");
        driver.executeScript("scrollBy(0,500)");
        return driver;
    }
}