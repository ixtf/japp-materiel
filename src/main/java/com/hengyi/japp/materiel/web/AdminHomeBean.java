package com.hengyi.japp.materiel.web;

import com.hengyi.japp.materiel.event.ImportSapMaraEvent;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.ocpsoft.rewrite.annotation.Join;

import com.hengyi.japp.web.AbstractBean;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.validation.constraints.Min;

@Named
@ViewScoped
@Join(path = "/admin", to = "/faces/admin/home.jsf")
public class AdminHomeBean extends AbstractBean {

    private static final long serialVersionUID = 1L;
    @Inject
    private Event<ImportSapMaraEvent> importSapMaraEvent;
    @Min(1)
    private int I_FIREST = 1;
    @Min(1)
    private int I_PAGESIZE = 100;

    public void importAllSapMara() {
        try {
            importSapMaraEvent.fire(new ImportSapMaraEvent());
        } catch (Exception e) {
            errorMessage(e);
        }
    }

    public void importSapMara() {
        try {
            importSapMaraEvent.fire(new ImportSapMaraEvent(I_FIREST, I_PAGESIZE));
        } catch (Exception e) {
            errorMessage(e);
        }
    }

    public int getI_FIREST() {
        return I_FIREST;
    }

    public void setI_FIREST(int I_FIREST) {
        this.I_FIREST = I_FIREST;
    }

    public int getI_PAGESIZE() {
        return I_PAGESIZE;
    }

    public void setI_PAGESIZE(int I_PAGESIZE) {
        this.I_PAGESIZE = I_PAGESIZE;
    }
}
