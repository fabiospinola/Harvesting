package com.appdetex.harvest.api;

import java.time.LocalDateTime;

public interface MarketplaceDetection {

    LocalDateTime getCapturedDate();

    String getTitle();

    String getDescription();

    String getUrl();

    String getImageUrl();

    Integer getOrder();

    String isPaid();

    String getPrice();

}
