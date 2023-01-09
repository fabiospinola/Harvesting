package ai.tracer.harvest.utils;

import java.util.ArrayList;

public class Failures {

    public ArrayList<String> failureReason = null;

    public Integer numberOfFailures = 0;

    public ArrayList<String> getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason){
        this.failureReason.add(failureReason);
    }

    public Integer getNumberOfFailures() {
        return numberOfFailures;
    }

    public void addNumberOfFailures() {
        this.numberOfFailures += 1;
    }
}
