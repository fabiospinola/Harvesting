package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.api.MarketplaceDetection;

import java.time.LocalDateTime;

public class MarketplaceDetectionItem implements MarketplaceDetection {
    private String title;
    private String description;
    private String url;
    private String imageUrl;
    private Integer order;
    private String paid;
    private String price;

    public MarketplaceDetectionItem(String title, String description, String url, String imageUrl, Integer order, String paid, String price) {
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
        return null;
    }

    @Override
    public Integer getOrder() {
        return null;
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public String getImageUrl() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getPaid() {
        return null;
    }

    @Override
    public String getPrice() {
        return null;
    }
}
