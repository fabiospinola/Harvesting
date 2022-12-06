package ai.tracer.harvest.rulesengine;

import ai.tracer.harvest.api.MarketplaceDetection;

import java.util.ArrayList;
import java.util.List;

public class RulesEngine {
    public static List<MarketplaceDetection> verifyDescription(List<MarketplaceDetection> detectionList, String term, String rule){
        //ArrayList<MarketplaceDetection> detections = new ArrayList<>();
        for(int i = 0; i < detectionList.size(); i++){
            if(detectionList.get(i).getDescription().contains(term) && rule == "enforcing"){
                detectionList.get(i).setState("open");
                detectionList.get(i).setStatus("enforcing");
                detectionList.get(i).setReason_code("brand misuse");
                detectionList.get(i).setAnalyst("Rules Engine");
            }
            if (detectionList.get(i).getDescription().contains(term) && rule == "fair-use") {
                detectionList.get(i).setState("closed");
                detectionList.get(i).setStatus("benign");
                detectionList.get(i).setReason_code("fair-use");
                detectionList.get(i).setAnalyst("Rules Engine");
            }
        }
        return detectionList;
    }
}
