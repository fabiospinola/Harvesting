package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.httpclient.Detections;
import com.appdetex.harvest.httpclient.service.DetectionService;
import com.appdetex.harvest.httpclient.entity.Detection;
import com.appdetex.harvest.httpclient.request.UpdateDetectionRequest;
import com.appdetex.harvest.httpclient.response.DetectionResponse;
import com.appdetex.harvest.api.HarvestException;
import com.appdetex.harvest.api.MarketplaceDetection;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public class Main {

    final static CloseableHttpClient httpClient = HttpClients.createDefault();
    public static void simpleGet() throws Exception{
        HttpGet request = new HttpGet("http://localhost:8080/Detections");

        CloseableHttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();
        // System.out.println(EntityUtils.toString(entity));

        if (response.getStatusLine().getStatusCode() != 200) {
            System.out.println("Bad connection. " + response.getStatusLine().getStatusCode());
            return;
        }

        ObjectMapper mapper = new ObjectMapper();

        List<Detections> detectionsList = new ArrayList<Detections>();
        int counter = 0;

        detectionsList = mapper.readValue(EntityUtils.toString(entity), new TypeReference<List<Detections>>() {});
        counter = detectionsList.size();

        System.out.println(counter + " detections found! ");
        for (Detections detections : detectionsList) {
            System.out.println(detections.toString());
        }
    }

    public static void parameterizedGet(int order) throws Exception {
        String URL = String.format("http://localhost:8080/Detections?order=%d", order);
        HttpGet request = new HttpGet(URL);

        CloseableHttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();
        // System.out.println(EntityUtils.toString(entity));

        if (response.getStatusLine().getStatusCode() != 200) {
            System.out.println("Bad connection. " + response.getStatusLine().getStatusCode());
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        List<Detections> detectionsList = new ArrayList<Detections>();
        int counter = 0;

        detectionsList = mapper.readValue(EntityUtils.toString(entity), new TypeReference<List<Detections>>() {});

        counter = detectionsList.size();

        System.out.println(counter + " detections found:");
        for (Detections detections : detectionsList) {
            System.out.println(detections.toString());
        }
    }
    public static void postADetection(List<MarketplaceDetection> detectionList) throws Exception {

        HttpPost request = new HttpPost("http://localhost:8080/api/detection/create");  // "Detections" é um nome provisório para a Base de Dados
        /*
        for (MarketplaceDetection detection : detectionList) {
            Detections addDetections = new Detections(detection.getTitle(), detection.getDescription(), detection.getUrl(), detection.getImageUrl(), detection.getOrder(), detection.getPaid(), detection.getPrice());
            ObjectMapper mapper = new ObjectMapper();
            StringEntity json = new StringEntity(mapper.writeValueAsString(addDetections), ContentType.APPLICATION_JSON);
            request.setEntity(json);
        }*/
        int i =0;
        for (i = 0; i < detectionList.size(); i++) {
            Detections addDetections = new Detections(detectionList.get(i).getTitle(), detectionList.get(i).getDescription(), detectionList.get(i).getUrl(), detectionList.get(i).getImageUrl(), detectionList.get(i).getOrder(), detectionList.get(i).getPaid(), detectionList.get(i).getPrice());
            ObjectMapper mapper = new ObjectMapper();
            StringEntity json = new StringEntity(mapper.writeValueAsString(addDetections), ContentType.APPLICATION_JSON);
            request.setEntity(json);
        }


        CloseableHttpResponse response = httpClient.execute(request);

        if (response.getStatusLine().getStatusCode() != 200) {
            System.out.println("No detections added! "+ response.getStatusLine().getStatusCode());
        }
    }
    @Autowired
    DetectionService detectionService;
    @PutMapping("update")
    public DetectionResponse updateDetection(@Valid @RequestBody UpdateDetectionRequest updateDetectionRequest) {

        Detection detection = detectionService.updateDetection(updateDetectionRequest);

        return new DetectionResponse(detection);

    }
    @DeleteMapping("delete/{id}")
    public String deleteStudent(@PathVariable("id") long id) {
        return detectionService.deleteDetection(id);

    }

    public static void main(String[] args) {
        //
        System.out.println("Leave me alone, I'm Scraping!!! (╯ ͠° ͟ʖ ͡°)╯┻━┻");
        AliexpressHarvester harvest = new AliexpressHarvester();
        AmazonUsHarvester harvest1 = new AmazonUsHarvester();
        AmazonUkHarvester harvest2 = new AmazonUkHarvester();
        List<MarketplaceDetection> detections = null;
        List<MarketplaceDetection> detections1 = null;
        List<MarketplaceDetection> detections2 = null;
        try {
            //detections = harvest.parseTarget("jacuzzi", 10);
            detections1 = harvest1.parseTarget("jacuzzi", 10);
            //detections2 = harvest2.parseTarget("jacuzzi", 10);
            postADetection(detections1);
        } catch (HarvestException | InterruptedException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Got your data? Now leave me to rest!!! ( ͠° ͟ʖ ͡°)");
    }
}