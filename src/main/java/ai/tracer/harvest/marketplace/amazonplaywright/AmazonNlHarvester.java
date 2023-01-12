package ai.tracer.harvest.marketplace.amazonplaywright;

public class AmazonNlHarvester extends AbstractAmazonPlaywright {
    public AmazonNlHarvester(){
        super("https://www.amazon.nl/-/en/s?k=","https://www.amazon.nl");
        //super("file:///Users/fabio.spinola/Documents/WebScraping/Harvesting/WebScraper/src/test/resources/amazonNlSearch.html","https://www.amazon.nl");
    }
}
