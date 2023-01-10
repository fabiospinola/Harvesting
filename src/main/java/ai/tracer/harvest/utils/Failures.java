package ai.tracer.harvest.utils;

import java.util.ArrayList;

public class Failures {

    public Integer numberOfConnectionFailure = 0;

    public Integer numberOfPathFailure = 0;

    public Failures(){

    }
    public Integer getNumberOfConnectionFailure() {
        return numberOfConnectionFailure;
    }

    public void addNumberOfConnectionFailure() {
        this.numberOfConnectionFailure += 1;
    }

    public Integer getNumberOfPathFailure() {
        return numberOfPathFailure;
    }

    public void addNumberOfPathFailure() {
        this.numberOfPathFailure += 1;
    }

}
