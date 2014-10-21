package com.hengyi.japp.materiel;

import com.hengyi.japp.event.DestroyEvent;
import javax.enterprise.event.Event;
import javax.faces.application.Application;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;
import javax.inject.Inject;

import com.hengyi.japp.event.InitEvent;
import javax.faces.event.PreDestroyApplicationEvent;

public class ApplicationListener implements SystemEventListener {

    @Inject
    private Event<InitEvent> initEvent;
    @Inject
    private Event<DestroyEvent> destroyEvent;

    @Override
    public void processEvent(SystemEvent event) throws AbortProcessingException {
        if (event instanceof PostConstructApplicationEvent) {
            initEvent.fire(new InitEvent());
        }
        if (event instanceof PreDestroyApplicationEvent) {
            destroyEvent.fire(new DestroyEvent());
        }
    }

    @Override
    public boolean isListenerForSource(Object source) {
        return (source instanceof Application);
    }

}
