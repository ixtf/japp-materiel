package com.hengyi.japp.materiel.web.mara;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.faces.navigate.Navigate;

import com.hengyi.japp.materiel.domain.SapMara;
import com.hengyi.japp.materiel.web.model.SapMarasDataModel;
import com.hengyi.japp.web.AbstractBean;

@Named
@ViewScoped
@Join(path = "/sapMaras", to = "/faces/mara/sapMaras.jsf")
public class SapMarasBean extends AbstractBean {

    private static final long serialVersionUID = 1L;
    @Inject
    private SapMarasDataModel sapMaras;
    private SapMara sapMara;

    public Navigate edit() {
        return Navigate.to(MaraBean.class).with("matnr", sapMara.getMatnr());
    }

    public SapMarasDataModel getSapMaras() {
        return sapMaras;
    }

    public void setSapMaras(SapMarasDataModel sapMaras) {
        this.sapMaras = sapMaras;
    }

    public SapMara getSapMara() {
        return sapMara;
    }

    public void setSapMara(SapMara sapMara) {
        this.sapMara = sapMara;
    }
}
