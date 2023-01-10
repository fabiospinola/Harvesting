package ai.tracer.harvest.harvestrunner;

import ai.tracer.harvest.stopwatch.ElapsedTimeItem;
import ai.tracer.harvest.tracerclient.Requests;
import ai.tracer.harvest.marketplace.amazonplaywright.AmazonEsHarvester;
import ai.tracer.harvest.marketplace.amazonplaywright.AmazonNlHarvester;
import ai.tracer.harvest.api.MarketplaceDetection;
import ai.tracer.harvest.marketplace.amazon.AmazonUkHarvester;
import ai.tracer.harvest.marketplace.amazon.AmazonUsHarvester;
import ai.tracer.harvest.marketplace.ebay.EbayUkHarvester;
import ai.tracer.harvest.marketplace.ebay.EbayUsHarvester;
import ai.tracer.harvest.stopwatch.Stopwatch;

import java.util.*;
import java.util.logging.Level;


public class HarvesterRunner {

    public static void main(String[] args) throws Exception {
        System.out.println("Leave me alone, I'm Scraping!!! (╯ ͠° ͟ʖ ͡°)╯┻━┻");
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

        AmazonEsHarvester harvestAES = new AmazonEsHarvester();
        AmazonNlHarvester harvestANL = new AmazonNlHarvester();
        AmazonUsHarvester harvestAUS = new AmazonUsHarvester();
        AmazonUkHarvester harvestAUK = new AmazonUkHarvester();
        EbayUsHarvester harvestEUS = new EbayUsHarvester();
        EbayUkHarvester harvestEUK = new EbayUkHarvester();
        Requests request = new Requests();
        List<MarketplaceDetection> detectionsAES; //List to save amazon.es detections
        List<MarketplaceDetection> detectionsANL; //List to save amazon.nl detections
        List<MarketplaceDetection> detectionsAUS; //List to save amazon.com detections
        List<MarketplaceDetection> detectionsAUK; //List to save amazon.co.uk detections
        List<MarketplaceDetection> detectionsEUS; //List to save ebay.com detections
        List<MarketplaceDetection> detectionsEUK; //List to save ebay.co.uk detections

        ArrayList<String> brandTracks = request.getBrandTracks();
        ArrayList<Long> customerIds = request.getCustomerIds();
        ArrayList<ElapsedTimeItem> elapsedTimeItems= new ArrayList<>();


        for (int i = 0; i < brandTracks.size(); i++) {
            int numItems = 5;
            try {
                Stopwatch stopwatch = new Stopwatch();
                stopwatch.start();
                detectionsAES = harvestAES.parseTarget(brandTracks.get(i), numItems,customerIds.get(i));
                stopwatch.stop();
                elapsedTimeItems.add(new ElapsedTimeItem(stopwatch.getElapsedTime(),"AmazonES", brandTracks.get(i)));

                stopwatch.start();
                detectionsANL = harvestANL.parseTarget(brandTracks.get(i), numItems, customerIds.get(i));
                stopwatch.stop();
                elapsedTimeItems.add(new ElapsedTimeItem(stopwatch.getElapsedTime(),"AmazonNL", brandTracks.get(i)));

                stopwatch.start();
                detectionsAUS = harvestAUS.parseTarget(brandTracks.get(i), numItems,customerIds.get(i));
                stopwatch.stop();
                elapsedTimeItems.add(new ElapsedTimeItem(stopwatch.getElapsedTime(),"AmazonUS", brandTracks.get(i)));

                stopwatch.start();
                detectionsAUK = harvestAUK.parseTarget(brandTracks.get(i), numItems,customerIds.get(i));
                stopwatch.stop();
                elapsedTimeItems.add(new ElapsedTimeItem(stopwatch.getElapsedTime(),"AmazonUK", brandTracks.get(i)));

                stopwatch.start();
                detectionsEUS = harvestEUS.parseTarget(brandTracks.get(i), numItems,customerIds.get(i));
                stopwatch.stop();
                elapsedTimeItems.add(new ElapsedTimeItem(stopwatch.getElapsedTime(),"EbayUS", brandTracks.get(i)));

                stopwatch.start();
                detectionsEUK = harvestEUK.parseTarget(brandTracks.get(i), numItems,customerIds.get(i));
                stopwatch.stop();
                elapsedTimeItems.add(new ElapsedTimeItem(stopwatch.getElapsedTime(),"EbayUK", brandTracks.get(i)));

                request.postADetection(detectionsAES,customerIds.get(i));

                request.postADetection(detectionsANL,customerIds.get(i));

                request.postADetection(detectionsAUS,customerIds.get(i));

                request.postADetection(detectionsAUK,customerIds.get(i));

                request.postADetection(detectionsEUS,customerIds.get(i));

                request.postADetection(detectionsEUK,customerIds.get(i));

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            // TODO: 05/01/2023 postTimePerHarvest

        }
        request.postTimes(elapsedTimeItems);
        System.out.println("Got your data? Now leave me to rest!!! ( ͠° ͟ʖ ͡°)");
        System.exit(0);
    }
}