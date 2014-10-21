package com.hengyi.japp.materiel.web.workshop;

import com.hengyi.japp.materiel.domain.Workshop;
import com.hengyi.japp.materiel.web.model.WorkshopsDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.faces.navigate.Navigate;

import com.hengyi.japp.web.AbstractBean;

@Named
@ViewScoped
@Join(path = "/workshops", to = "/faces/workshop/list.jsf")
public class WorkshopsBean extends AbstractBean {

    private static final long serialVersionUID = 1L;
    @Inject
    private WorkshopsDataModel workshops;
    private Workshop workshop;

    public Navigate edit() {
        return Navigate.to(WorkshopBean.class).with("id", workshop.getId());
    }

    public WorkshopsDataModel getWorkshops() {
        return workshops;
    }

    public void setWorkshops(WorkshopsDataModel workshops) {
        this.workshops = workshops;
    }

    public Workshop getWorkshop() {
        return workshop;
    }

    public void setWorkshop(Workshop workshop) {
        this.workshop = workshop;
    }
}
