package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.api.MarketplaceDetection;

public class MarketplaceDetectionItem implements MarketplaceDetection {
    private String title;
    private String description;
    private String url;
    private String imageUrl;
    private Integer order;
    private String sponsored;
    private String price;

    public MarketplaceDetectionItem(String title, String description, String url, String imageUrl, Integer order, String sponsored, String price) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.imageUrl = imageUrl;
        this.order = order;
        this.sponsored = sponsored;
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

    @Override
    public String getSponsored() {
        return sponsored;
    }

    @Override
    public String getPrice() {
        return price;
    }
}
