import com.appdetex.harvest.marketplace.EbayUkHarvester;

public class EbayUkHarvTest extends AbstractEbayHarvTest {
    public EbayUkHarvTest() {
        super("file:///Users/sara.teixeira/Documents/HarvestingProject/Harvesting/src/test/resources/eBayUk.html", "file:///Users/sara.teixeira/Documents/HarvestingProject/Harvesting/src/test/resources/eBayUkEmpty.html", new EbayUkHarvester());
    }
}
