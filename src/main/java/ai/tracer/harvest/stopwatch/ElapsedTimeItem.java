package ai.tracer.harvest.stopwatch;

import ai.tracer.harvest.api.ElapsedTime;

public class ElapsedTimeItem implements ElapsedTime {

    private Long time;

    private String harvester;

    public ElapsedTimeItem(Long time, String harvester){
        this.time = time;
        this.harvester = harvester;

    }

    @Override
    public Long getTime() {
        return this.time;
    }

    @Override
    public String getHarvester() {
        return this.harvester;
    }
}
