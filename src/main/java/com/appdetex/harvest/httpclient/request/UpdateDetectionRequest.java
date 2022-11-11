package com.appdetex.harvest.httpclient.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateDetectionRequest {

    @NotNull(message = "Detection id required")
    private Long id;
    private String title;
    private String description;
    private String url;
    private String imageUrl;
    private String order;
    private String sponsored;
    private String price;

}

