package com.appdetex.harvest.api;

import java.time.LocalDateTime;

public interface MarketplaceDetection {

    String getTitle();
    LocalDateTime getCapturedDate();

    String getDescription();

    String getUrl();

    String getImageUrl();

    Integer getOrder();

    String getPaid();

    String getPrice();
}
