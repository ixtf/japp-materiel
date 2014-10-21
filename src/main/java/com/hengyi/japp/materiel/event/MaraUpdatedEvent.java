package com.hengyi.japp.materiel.event;

public class MaraUpdatedEvent {

    private final String id;

    public MaraUpdatedEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
