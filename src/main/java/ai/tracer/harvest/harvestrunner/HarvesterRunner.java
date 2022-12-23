package ai.tracer.harvest.harvestrunner;

import ai.tracer.harvest.httpclient.Requests;
import ai.tracer.harvest.marketplace.amazonplaywright.AmazonESHarvester;
import ai.tracer.harvest.marketplace.amazonplaywright.AmazonNLHarvester;
import ai.tracer.harvest.api.MarketplaceDetection;
import ai.tracer.harvest.marketplace.amazon.AmazonUkHarvester;
import ai.tracer.harvest.marketplace.amazon.AmazonUsHarvester;
import ai.tracer.harvest.marketplace.ebay.EbayUkHarvester;
import ai.tracer.harvest.marketplace.ebay.EbayUsHarvester;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class HarvesterRunner {

    public static void main(String[] args) throws Exception {
        System.out.println("Leave me alone, I'm Scraping!!! (╯ ͠° ͟ʖ ͡°)╯┻━┻");

        AmazonESHarvester harvestAES = new AmazonESHarvester();
        AmazonNLHarvester harvestANL = new AmazonNLHarvester();
        AmazonUsHarvester harvestAUS = new AmazonUsHarvester();
        AmazonUkHarvester harvestAUK = new AmazonUkHarvester();
        EbayUsHarvester harvestEUS = new EbayUsHarvester();
        EbayUkHarvester harvestEUK = new EbayUkHarvester();
        List<MarketplaceDetection> detectionsAES; //List to save amazon.es detections
        List<MarketplaceDetection> detectionsANL; //List to save amazon.nl detections
        List<MarketplaceDetection> detectionsAUS; //List to save amazon.com detections
        List<MarketplaceDetection> detectionsAUK; //List to save amazon.co.uk detections
        List<MarketplaceDetection> detectionsEUS; //List to save ebay.com detections
        List<MarketplaceDetection> detectionsEUK; //List to save ebay.co.uk detections

        ArrayList<String> brandTracks = Requests.getBrandTracks();
        ArrayList<Long> customerIds = Requests.getCustomerIds();

        long startTime = System.currentTimeMillis( );
        for (int i = 0; i < brandTracks.size(); i++) {
            int numItems = 10;
            try {

                detectionsAES = harvestAES.parseTarget(brandTracks.get(i), numItems,customerIds.get(i));
                Requests.postADetection(detectionsAES);
                detectionsANL = harvestANL.parseTarget(brandTracks.get(i), numItems,customerIds.get(i));
                Requests.postADetection(detectionsANL);
                //detectionsAUS = harvestAUS.parseTarget(brandTracks.get(i), numItems,customerIds.get(i));
                //Requests.postADetection(detectionsAUS);
                detectionsAUK = harvestAUK.parseTarget(brandTracks.get(i), numItems,customerIds.get(i));
                Requests.postADetection(detectionsAUK);
                detectionsEUS = harvestEUS.parseTarget(brandTracks.get(i), numItems,customerIds.get(i));
                Requests.postADetection(detectionsEUS);
                detectionsEUK = harvestEUK.parseTarget(brandTracks.get(i), numItems,customerIds.get(i));
                Requests.postADetection(detectionsEUK);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        long endTime = System.currentTimeMillis( );
        long elapsedTime = endTime-startTime;
        long averagePerBrand = elapsedTime/brandTracks.size();
        long minutesTime = TimeUnit.MILLISECONDS.toMinutes(elapsedTime);
        long secondsTime = (TimeUnit.MILLISECONDS.toSeconds(elapsedTime) % 60);
        long minutesBrand = TimeUnit.MILLISECONDS.toMinutes(averagePerBrand);
        long secondsBrand = (TimeUnit.MILLISECONDS.toSeconds(averagePerBrand) % 60);
        System.out.println("Elapsed Time: " + minutesTime + "m" + secondsTime + "s");
        System.out.println("Average time per brand: " + minutesBrand + "m" + secondsBrand + "s");

        //long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime);
        System.out.println("Got your data? Now leave me to rest!!! ( ͠° ͟ʖ ͡°)");
    }
}