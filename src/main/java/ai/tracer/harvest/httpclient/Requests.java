package ai.tracer.harvest.httpclient;

import ai.tracer.harvest.api.MarketplaceDetection;
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
import java.util.List;

public class Requests {

    public void postADetection(List<MarketplaceDetection> detectionList) throws Exception {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        int counter;
        for (counter = 0; counter < detectionList.size(); counter++) {
            HttpPost request = new HttpPost("http://localhost:8080/api/detection");

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

    public ArrayList<String> getBrandTracks() throws Exception {
        ArrayList<String> brandTracksArrayList = new ArrayList<>();
        JSONArray obj;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        final HttpGet httpGet = new HttpGet("http://localhost:8080/api/brandtracks");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        try (httpClient) {
            try{
                StatusLine statusLine = response.getStatusLine();
                System.out.println(statusLine.getStatusCode() + " CONNECTION OK! " + statusLine.getReasonPhrase());
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
                System.out.println("# "+ i + " " + obj.getJSONObject(i).getString("term"));
                brandTracksArrayList.add(obj.getJSONObject(i).getString("term"));
            }
        }
        response.close();
        return brandTracksArrayList;
    }
    public ArrayList<Long> getCustomerIds() throws Exception {
        ArrayList<Long> customerIds = new ArrayList<>();
        JSONArray obj;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        final HttpGet httpGet = new HttpGet("http://localhost:8080/api/brandtracks");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        try (httpClient) {
            try{
                StatusLine statusLine = response.getStatusLine();
                System.out.println(statusLine.getStatusCode() + " CONNECTION OK! " + statusLine.getReasonPhrase());
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
                System.out.println("# "+ i + " " + obj.getJSONObject(i).getString("customerId"));
                customerIds.add(Long.valueOf(obj.getJSONObject(i).getString("customerId")));
            }
        }
        response.close();
        return customerIds;
    }

}
