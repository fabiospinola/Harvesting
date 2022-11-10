package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.api.MarketplaceDetection;

public class MarketplaceDetectionItem implements MarketplaceDetection {

    private String title;
    private String description;
    private String url;
    private String imageUrl;
    private Integer order;
    private String paid;
    private String price;

    public MarketplaceDetectionItem( String title, String description, String url, String imageUrl, Integer order, String paid, String price) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.imageUrl = imageUrl;
        this.order = order;
        this.paid = paid;
        this.price = price;
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

    public String getPaid() {
        return paid;
    }

    @Override
    public String getPrice() {
        return price;
    }
}
