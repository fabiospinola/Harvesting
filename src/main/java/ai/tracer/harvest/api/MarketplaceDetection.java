package ai.tracer.harvest.api;

public interface MarketplaceDetection {

    String getTitle();

    String getDescription();

    String getUrl();

    String getImageUrl();

    Integer getOrder();

    String getSponsored();

    String getPrice();
    
    String getState();
    
    String getStatus();

    String getReasonCode();

    Long getAnalystId();

    Long getCustomerId();

    String setState(String state);
    String setStatus(String state);
    String setReasonCode(String state);
    Long setAnalystId(Long analystId);
    Long setCustomerId(Long customerId);
}
