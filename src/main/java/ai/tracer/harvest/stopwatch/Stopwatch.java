package ai.tracer.harvest.stopwatch;

import java.util.concurrent.TimeUnit;


public class Stopwatch {
    private long startTime = 0;
    private long stopTime = 0;
    private boolean running = false;

    public void start(){
        this.startTime = System.currentTimeMillis( );
        this.running = true;
    }
    public void stop(){
         this.stopTime = System.currentTimeMillis( );
         this.running = false;
    }

    public Long getElapsedTime() {
        long elapsedTime;
        if (running) {
            elapsedTime = (System.currentTimeMillis() - startTime);
        } else {
            elapsedTime = (stopTime - startTime);
        }
        return TimeUnit.MILLISECONDS.toSeconds(elapsedTime);
    }
}


