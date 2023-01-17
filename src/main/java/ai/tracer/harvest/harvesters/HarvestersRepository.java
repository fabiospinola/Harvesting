package ai.tracer.harvest.harvesters;

import ai.tracer.harvest.api.MarketplaceHarvester;
import ai.tracer.harvest.harvesters.amazon.AmazonUkHarvester;
import ai.tracer.harvest.harvesters.amazon.AmazonUsHarvester;
import ai.tracer.harvest.harvesters.amazonplaywright.AmazonEsHarvester;
import ai.tracer.harvest.harvesters.amazonplaywright.AmazonNlHarvester;
import ai.tracer.harvest.harvesters.ebay.EbayUkHarvester;
import ai.tracer.harvest.harvesters.ebay.EbayUsHarvester;

import java.util.ArrayList;
import java.util.List;

public class HarvestersRepository {

    public List<MarketplaceHarvester> listHarvesters() {
        List<MarketplaceHarvester> harvesters = new ArrayList<>();
        harvesters.add(new AmazonEsHarvester());
        harvesters.add(new AmazonNlHarvester());
        harvesters.add(new AmazonUsHarvester());
        harvesters.add(new AmazonUkHarvester());
        harvesters.add(new EbayUsHarvester());
        harvesters.add(new EbayUkHarvester());
        return harvesters;
    }
}
