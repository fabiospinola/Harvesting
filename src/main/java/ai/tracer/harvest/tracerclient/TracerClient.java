package ai.tracer.harvest.tracerclient;

import ai.tracer.harvest.api.MarketplaceDetection;
import ai.tracer.harvest.stopwatch.HarvesterAnalytics;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TracerClient {

    public void postADetection(List<MarketplaceDetection> detectionList, Long customerId) throws Exception {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        int counter;
        for (counter = 0; counter < detectionList.size(); counter++) {
            HttpPost request = new HttpPost("http://localhost:8080/api/detection");

            ObjectMapper mapper = new ObjectMapper();
            DetectionJSONCreator creator = new DetectionJSONCreator(detectionList.get(counter),new Customer(customerId));
            String detectionResponse = mapper.writeValueAsString(creator);
            //System.out.println(detectionResponse);
            StringEntity json = new StringEntity(detectionResponse, ContentType.APPLICATION_JSON); // TODO: 04/01/2023 substituir para detectionTest
            request.setEntity(json);
            CloseableHttpResponse response = httpClient.execute(request);

            if (response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 201) {
                System.out.println("No detections added! " + response.getStatusLine().getStatusCode());
            } else if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201) {
                System.out.println("New detection added!");
            }
            response.close();
        }
    }

    public void postHarvesterMetrics(HarvesterAnalytics harvesterAnalytics) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost("http://localhost:8080/api/harvester-analytic");
        ObjectMapper mapper = new ObjectMapper();

        String timeResponse = mapper.writeValueAsString(harvesterAnalytics);
        System.out.println(timeResponse);

        StringEntity json = new StringEntity(timeResponse, ContentType.APPLICATION_JSON);
        request.setEntity(json);
        CloseableHttpResponse response = httpClient.execute(request);

        if (response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 201) {
            System.out.println("No times added! " + response.getStatusLine().getStatusCode());
        } else if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201) {
            System.out.println("New time added!");
        }
        response.close();
    }

    public Map<String,Long> fetchBrandTracks() throws Exception {
        ArrayList<String> brandTracksArrayList = new ArrayList<>();
        Map<String,Long> myMap = new HashMap<>();
        JSONArray obj;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        final HttpGet httpGet = new HttpGet("http://localhost:8080/api/brand-tracks");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        try (httpClient) {
            try{
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() != 200) {
                    System.out.println("Connection Refused! " + response.getStatusLine().getStatusCode());
                } else if (statusLine.getStatusCode() == 200) {
                    System.out.println("Connection OK!");
                }
                String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                obj = new JSONArray(responseBody);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            for(int i = 0; i < obj.length(); i++){
                //System.out.println("# "+ i + " " + obj.getJSONObject(i).getString("term"));
                myMap.put(obj.getJSONObject(i).getString("term"),Long.valueOf(obj.getJSONObject(i).getJSONObject("customer").getString("id")));
                System.out.println(myMap);
            }
        }
        response.close();
        return myMap;
    }
    public ArrayList<Long> fetchCustomerIds() throws Exception {
        ArrayList<Long> customerIds = new ArrayList<>();
        JSONArray obj;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        final HttpGet httpGet = new HttpGet("http://localhost:8080/api/brand-tracks");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        try (httpClient) {
            try{
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() != 200) {
                    System.out.println("Connection Refused! " + response.getStatusLine().getStatusCode());
                } else if (statusLine.getStatusCode() == 200) {
                    System.out.println("Connection OK!");
                }
                String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                obj = new JSONArray(responseBody);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            for(int i = 0; i < obj.length(); i++){

                System.out.println("# "+ i + " " + obj.getJSONObject(i).getJSONObject("customer").getString("id"));
                customerIds.add(Long.valueOf(obj.getJSONObject(i).getJSONObject("customer").getString("id")));
            }
        }
        response.close();
        return customerIds;
    }

}
