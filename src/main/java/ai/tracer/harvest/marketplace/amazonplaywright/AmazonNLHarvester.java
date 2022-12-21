package ai.tracer.harvest.marketplace.amazonplaywright;

public class AmazonNLHarvester extends AbstractAmazonEUHarvester{
    public AmazonNLHarvester(){
        super("https://www.amazon.nl/-/en/s?k=","https://www.amazon.nl");
        //super("file:///Users/fabio.spinola/Documents/WebScraping/Harvesting/WebScraper/src/test/resources/amazonNlSearch.html","https://www.amazon.nl");
    }
}
