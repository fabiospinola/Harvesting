package ai.tracer.harvest.httpclient;

import ai.tracer.harvest.api.MarketplaceDetection;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Operations {
    final static CloseableHttpClient httpClient = HttpClients.createDefault();

    public static void postADetection(List<MarketplaceDetection> detectionList) throws Exception {

        int counter;
        for (counter = 0; counter < detectionList.size(); counter++) {
            HttpPost request = new HttpPost("http://localhost:8080/api/createDetection");

            ObjectMapper mapper = new ObjectMapper();
            StringEntity json = new StringEntity(mapper.writeValueAsString(detectionList.get(counter)), ContentType.APPLICATION_JSON);
            request.setEntity(json);
            CloseableHttpResponse response = httpClient.execute(request);

            if (response.getStatusLine().getStatusCode() != 200) {
                System.out.println("No detections added! " + response.getStatusLine().getStatusCode());
            } else if (response.getStatusLine().getStatusCode() == 200) {
                System.out.println("New detection added!");
            }
            response.close();
        }
    }
    public static ArrayList<String> getSearchTerm() throws IOException {
        HttpGet request = new HttpGet("http://localhost:8080/api/searchterm");
        HttpResponse httpresponse = httpClient.execute(request);
        ArrayList<String> terms = new ArrayList<>();
        Scanner sc = new Scanner(httpresponse.getEntity().getContent());
        while(sc.hasNext()) {
            terms.add(sc.nextLine());
        }
        return terms;
    }
}
