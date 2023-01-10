package ai.tracer.harvest.api;

public interface ElapsedTime {

    void setTime(Long time);

    Long getTime();

    String getHarvester();

    String getBrandTrack();
}
