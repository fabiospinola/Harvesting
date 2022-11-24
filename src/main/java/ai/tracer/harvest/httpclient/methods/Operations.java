package ai.tracer.harvest.httpclient.methods;

import ai.tracer.harvest.api.MarketplaceDetection;
import ai.tracer.harvest.httpclient.Detections;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.List;

public class Operations {
    final static CloseableHttpClient httpClient = HttpClients.createDefault();

    public static void postADetection(List<MarketplaceDetection> detectionList) throws Exception {

        int counter;
        for (counter = 0; counter < detectionList.size(); counter++) {
            HttpPost request = new HttpPost("http://localhost:8080/api/detection/create");
            Detections addDetections = new Detections(detectionList.get(counter).getTitle(), detectionList.get(counter).getDescription(), detectionList.get(counter).getUrl(), detectionList.get(counter).getImageUrl(), detectionList.get(counter).getOrder(), detectionList.get(counter).getSponsored(), detectionList.get(counter).getPrice());
            ObjectMapper mapper = new ObjectMapper();
            StringEntity json = new StringEntity(mapper.writeValueAsString(addDetections), ContentType.APPLICATION_JSON);
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
}
