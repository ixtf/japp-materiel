package com.hengyi.japp.materiel.event;

public class WorkshopUpdatedEvent {

    private final String id;

    public WorkshopUpdatedEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
