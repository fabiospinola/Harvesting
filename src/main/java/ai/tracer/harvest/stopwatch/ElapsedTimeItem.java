package ai.tracer.harvest.stopwatch;

import ai.tracer.harvest.api.ElapsedTime;

public class ElapsedTimeItem implements ElapsedTime {

    private Long time;

    private String harvester;

    private String brandTrack;

    public ElapsedTimeItem(Long time, String harvester, String brandTrack){
        this.time = time;
        this.harvester = harvester;
        this.brandTrack = brandTrack;

    }
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

    @Override
    public String getBrandTrack(){
        return this.brandTrack;
    }
}