package com.appdetex.harvest.httpclient.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDetectionRequest {
    //@JsonProperty("First_Name")
    //@NotBlank(message = "First name is required.")
    private String title;
    private String description;
    private String url;
    private String imageUrl;
    private String order;
    private String sponsored;
    private String price;

}
