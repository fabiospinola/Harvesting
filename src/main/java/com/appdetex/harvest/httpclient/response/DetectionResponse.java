package com.appdetex.harvest.httpclient.response;

import lombok.Getter;
import lombok.Setter;
import com.appdetex.harvest.httpclient.entity.Detection;

@Getter
@Setter

public class DetectionResponse {
    private long id;
    //@JsonProperty("Title")
    private String title;
    private String description;
    private String url;
    private String imageUrl;
    private String order;
    private String sponsored;
    private String price;

    public DetectionResponse(Detection detection) {
        this.id = detection.getId();
        this.title = detection.getTitle();
        this.description = detection.getDescription();
        this.url = detection.getUrl();
        this.imageUrl = detection.getImageUrl();
        this.order = detection.getOrder();
        this.sponsored = detection.getSponsored();
        this.price = detection.getPrice();
    }
}
