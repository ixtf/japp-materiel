package com.hengyi.japp.materiel.web;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import org.ocpsoft.rewrite.annotation.Join;

import com.hengyi.japp.materiel.service.QueryService;
import com.hengyi.japp.web.AbstractBean;

@Named
@ViewScoped
@Join(path = "/theme", to = "/faces/theme.jsf")
public class ThemeBean extends AbstractBean implements Serializable {

    private static final long serialVersionUID = 1L;
    @EJB
    private QueryService queryService;
}
