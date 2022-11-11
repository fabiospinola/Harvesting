package com.appdetex.harvest.httpclient;

public class Detections {

    private String title;
    private String description;
    private String url;
    private String imageUrl;
    private Integer order;
    private String sponsored;
    private String price;

    public Detections(String title, String description, String url, String imageUrl, Integer order, String sponsored, String price) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.imageUrl = imageUrl;
        this.order = order;
        this.sponsored = sponsored;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getSponsored() {
        return sponsored;
    }

    public void setSponsored(String sponsored) {
        this.sponsored = sponsored;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

