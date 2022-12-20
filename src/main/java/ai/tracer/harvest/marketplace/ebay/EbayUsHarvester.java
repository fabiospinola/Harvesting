package ai.tracer.harvest.marketplace.ebay;

public class EbayUsHarvester extends AbstractEbayHarvester {
    public EbayUsHarvester() {
        super("https://www.ebay.com/sch/i.html?_nkw=%s");
    }

}
