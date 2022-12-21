package ai.tracer.harvest.marketplace.amazonplaywright;

public class AmazonESHarvester extends AbstractAmazonEUHarvester{

    public AmazonESHarvester(){
        super("https://www.amazon.es/s?k=","https://www.amazon.es");
        //super("file:///Users/fabio.spinola/Documents/WebScraping/Harvesting/WebScraper/src/test/resources/amazonEsSearch.html","https://www.amazon.es");
    }

}
