package ai.tracer.harvest.harvestrunner;

import ai.tracer.harvest.api.MarketplaceHarvester;
import ai.tracer.harvest.harvesters.HarvestersRepository;
import ai.tracer.harvest.tracerclient.TracerClient;
import ai.tracer.harvest.api.MarketplaceDetection;

import java.util.*;
import java.util.logging.Level;


public class RunHarvesters {

    public static void main(String[] args) throws Exception {
        System.out.println("Leave me alone, I'm Scraping!!! (╯ ͠° ͟ʖ ͡°)╯┻━┻");
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

        HarvestersRepository harvestersRepository = new HarvestersRepository();
        List<MarketplaceHarvester> harvesterList = harvestersRepository.listHarvesters();

        TracerClient tracerClient = new TracerClient();

        Map<String,Long> brandTracks = tracerClient.fetchBrandTracks();
        for (MarketplaceHarvester harvester : harvesterList) {
            for (Map.Entry<String, Long> entry : brandTracks.entrySet()) {
                int numItems = 3;
                try {
                    List<MarketplaceDetection> detections = harvester.parseTarget(entry.getKey(), numItems, entry.getValue());
                    tracerClient.postADetection(detections, entry.getValue());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        System.out.println("Got your data? Now leave me to rest!!! ( ͠° ͟ʖ ͡°)");
        System.exit(0);
    }
}