package com.hengyi.japp.materiel.web;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.ocpsoft.rewrite.annotation.Join;

import com.hengyi.japp.web.AbstractBean;

@Named
@ViewScoped
@Join(path = "/", to = "/faces/home.jsf")
public class HomeBean extends AbstractBean implements Serializable {

}
