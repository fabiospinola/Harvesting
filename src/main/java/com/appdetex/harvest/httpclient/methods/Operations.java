package com.appdetex.harvest.httpclient.methods;

import com.appdetex.harvest.api.MarketplaceDetection;
import com.appdetex.harvest.httpclient.Detections;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.List;

public class Operations {
    final static CloseableHttpClient httpClient = HttpClients.createDefault();

    public static void simpleGet() throws Exception {
        HttpGet request = new HttpGet("http://localhost:8080/api/detection/getAll");

        CloseableHttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();
        System.out.println(EntityUtils.toString(entity));

        if (response.getStatusLine().getStatusCode() != 200) {
            System.out.println("Bad connection. " + response.getStatusLine().getStatusCode());
            return;
        }

        response.close();
    }

    public static void parameterizedGet(int id) throws Exception {
        String URL = String.format("http://localhost:8080/api/detection/id/%d", id);
        HttpGet request = new HttpGet(URL);

        CloseableHttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();
        System.out.println(EntityUtils.toString(entity));

        if (response.getStatusLine().getStatusCode() != 200) {
            System.out.println("Bad connection. " + response.getStatusLine().getStatusCode());
        }
    }

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

    public static String deleteDetection(int id) throws Exception {
        parameterizedGet(id); //just to show which row will be deleted
        String URL = String.format("http://localhost:8080/api/detection/delete/%d", id);
        HttpDelete request = new HttpDelete(URL);
        System.out.println("Executing request " + request.getRequestLine());

        CloseableHttpResponse response = httpClient.execute(request);

        if (response.getStatusLine().getStatusCode() != 200) {
            System.out.println("No detection deleted! " + response.getStatusLine().getStatusCode());
        } else if (response.getStatusLine().getStatusCode() == 200) {
            System.out.println("Detection Deleted!");
        }
        System.out.println("----------------------------------------");
        System.out.println(response);
        response.close();
        return ("Detection Deleted!");
    }
}
