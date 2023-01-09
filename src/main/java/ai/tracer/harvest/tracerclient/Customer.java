package ai.tracer.harvest.tracerclient;

import ai.tracer.harvest.api.CustomerInterface;

public class Customer implements CustomerInterface {

    private Long id;

    public Customer( Long id) {
        this.id = id;
    }

    @Override
    public Long getId() { return id; }

    @Override
    public Long setId(Long id) {
        this.id = id;
        return this.id;
    }
}
