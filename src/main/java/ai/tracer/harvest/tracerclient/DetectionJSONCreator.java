package ai.tracer.harvest.tracerclient;

import ai.tracer.harvest.api.MarketplaceDetection;

public class DetectionJSONCreator {

    private final MarketplaceDetection detection;

    private final Customer customer;

    public DetectionJSONCreator(MarketplaceDetection detection, Customer customer) {
        this.detection = detection;
        this.customer = customer;
    }


    public MarketplaceDetection getDetection() {
        return detection;
    }

    public Customer getCustomer() {
        return customer;
    }

}
