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

    String getReason_code();

    String getAnalyst();

    Long getCustomer_id();

    String setState(String state);
    String setStatus(String state);
    String setReason_code(String state);
    String setAnalyst(String state);
    Long setCustomerId(Long customer_id);
}
