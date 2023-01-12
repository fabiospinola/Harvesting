package ai.tracer.harvest.stopwatch;

import ai.tracer.harvest.api.ElapsedTime;

public class HarvesterAnalytics implements ElapsedTime {

    private Long time;

    private String harvester;

    private String brandTrack;

    private Integer connectionFailure = 0;

    private Integer pathFailure = 0;
    /*public HarvesterAnalytics(Long time, String harvester, String brandTrack, Integer connectionFailure, Integer pathFailure){
        this.time = time;
        this.harvester = harvester;
        this.brandTrack = brandTrack;
        this.connectionFailure = connectionFailure;
        this.pathFailure = pathFailure;

    }*/
    @Override
    public void setTime(Long time){
        this.time = time;
    }
    @Override
    public Long getTime() {
        return this.time;
    }

    @Override
    public String getHarvester() {
        return this.harvester;
    }
    public void setHarvester(String harvester) {
        this.harvester = harvester;
    }

    public void setBrandTrack(String brandTrack) {
        this.brandTrack = brandTrack;
    }

    @Override
    public String getBrandTrack(){
        return this.brandTrack;
    }

    public Integer getConnectionFailure() {
        return connectionFailure;
    }

    public void addConnectionFailure() {
        this.connectionFailure += 1;
    }

    public Integer getPathFailure() {
        return pathFailure;
    }

    public void addPathFailure() {
        this.pathFailure += 1;
    }

}
