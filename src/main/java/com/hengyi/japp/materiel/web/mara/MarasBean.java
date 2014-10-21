package com.hengyi.japp.materiel.web.mara;

import com.hengyi.japp.materiel.domain.Mara;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.faces.navigate.Navigate;

import com.hengyi.japp.materiel.web.model.MarasDataModel;
import com.hengyi.japp.web.AbstractBean;

@Named
@ViewScoped
@Join(path = "/maras", to = "/faces/mara/list.jsf")
public class MarasBean extends AbstractBean {

    private static final long serialVersionUID = 1L;
    @Inject
    private MarasDataModel maras;
    private Mara mara;

    public Navigate edit() {
        return Navigate.to(MaraBean.class).with("matnr", mara.getMatnr());
    }

    public MarasDataModel getMaras() {
        return maras;
    }

    public void setMaras(MarasDataModel maras) {
        this.maras = maras;
    }

    public Mara getMara() {
        return mara;
    }

    public void setMara(Mara mara) {
        this.mara = mara;
    }
}
