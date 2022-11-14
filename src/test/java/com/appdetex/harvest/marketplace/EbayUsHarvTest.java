import com.appdetex.harvest.marketplace.EbayUsHarvester;

public class EbayUsHarvTest extends AbstractEbayHarvTest {
    public EbayUsHarvTest() {
        super("file:///Users/sara.teixeira/Documents/HarvestingProject/Harvesting/src/test/resources/eBayUs.html","file:///Users/sara.teixeira/Documents/HarvestingProject/Harvesting/src/test/resources/eBayUsEmpty.html", new EbayUsHarvester());
    }
}
