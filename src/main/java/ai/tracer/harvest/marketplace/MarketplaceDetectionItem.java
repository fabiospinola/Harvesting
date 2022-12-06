package ai.tracer.harvest.marketplace;

import ai.tracer.harvest.api.MarketplaceDetection;

public class MarketplaceDetectionItem implements MarketplaceDetection {

    private String title;
    private String description;
    private String url;
    private String imageUrl;
    private Integer order;
    private String sponsored;
    private String price;
    private String state;
    private String status;
    private String reason_code;
    private String analyst;

    public MarketplaceDetectionItem(String title, String description, String url, String imageUrl, Integer order, String sponsored, String price, String state, String status, String reason_code, String analyst) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.imageUrl = imageUrl;
        this.order = order;
        this.sponsored = sponsored;
        this.price = price;
        this.state = state;
        this.status = status;
        this.reason_code = reason_code;
        this.analyst = analyst;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Integer getOrder() {
        return order;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getSponsored() {
        return sponsored;
    }

    @Override
    public String getPrice() {
        return price;
    }
    @Override
    public String getState() {
        return state;
    }
    @Override
    public String getStatus() {
        return status;
    }
    @Override
    public String getReason_code() {
        return reason_code;
    }
    @Override
    public String getAnalyst() {
        return analyst;
    }

    @Override
    public String setState(String state) {
        this.state = state;
        return this.state;
    }

    @Override
    public String setStatus(String status) {
        this.status = status;
        return this.status;
    }

    @Override
    public String setReason_code(String reason_code) {
        this.reason_code = reason_code;
        return this.reason_code;
    }

    @Override
    public String setAnalyst(String analyst) {
        this.analyst = analyst;
        return this.analyst;
    }

}
