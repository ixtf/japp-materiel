package com.hengyi.japp.materiel.web.workshop;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.Parameter;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.faces.annotation.Deferred;

import com.hengyi.japp.materiel.domain.Workshop;
import com.hengyi.japp.materiel.event.WorkshopUpdatedEvent;
import com.hengyi.japp.materiel.service.CacheService;
import com.hengyi.japp.materiel.service.OperatorService;
import com.hengyi.japp.materiel.service.QueryService;
import com.hengyi.japp.web.AbstractBean;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Named
@ViewScoped
@Join(path = "/workshop", to = "/faces/workshop/update.jsf")
public class WorkshopBean extends AbstractBean {

    private static final long serialVersionUID = 1L;
    @Inject
    private Event<WorkshopUpdatedEvent> workshopUpdatedEvent;
    @EJB
    private CacheService cacheService;
    @EJB
    private QueryService queryService;
    @EJB
    private OperatorService operatorService;
    @Deferred
    @Parameter
    private String id;
    private Workshop workshop;

    @Deferred
    @RequestAction
    public void requestAction() {
        if (workshop != null) {
            return;
        }

        if (id == null) {
            workshop = new Workshop();
        } else {
            workshop = queryService.find(Workshop.class, id);
        }
    }

    public void save() {
        try {
            workshop.getLogInfo()._operator(cacheService.getCurrentOperator());
            operatorService.save(workshop);
            workshopUpdatedEvent.fire(new WorkshopUpdatedEvent(workshop.getId()));
            operationSuccessMessage();
        } catch (Exception e) {
            errorMessage(e);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Workshop getWorkshop() {
        return workshop;
    }

    public void setWorkshop(Workshop workshop) {
        this.workshop = workshop;
    }
}
