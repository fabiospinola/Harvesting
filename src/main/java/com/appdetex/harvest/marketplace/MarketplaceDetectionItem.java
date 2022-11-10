package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.api.MarketplaceDetection;

public class MarketplaceDetectionItem implements MarketplaceDetection {

    private String capturedDate;
    private String title;
    private Integer order;
    private String url;
    private String imageUrl;
    private String description;
    private String price;
    private Boolean isPaid;

    public MarketplaceDetectionItem(String capturedDate, Integer order, String title, String description, String url, String imageUrl, String price, Boolean isPaid) {
        this.title = title;
        this.capturedDate = capturedDate;
        this.order = order;
        this.url = url;
        this.imageUrl = imageUrl;
        this.description = description;
        this.price = price;
        this.isPaid = isPaid;
    }

    @Override
    public String getTitle(){
        return title;
    }

    @Override
    public String getCapturedDate() {
        return capturedDate;
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
    public String getPrice() {
        return price;
    }

    @Override
    public Boolean isPaid() {
        return isPaid;
    }
}
