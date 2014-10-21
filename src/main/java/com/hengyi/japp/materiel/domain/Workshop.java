/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hengyi.japp.materiel.domain;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author jzb
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "T_WORKSHOP")
@NamedQueries({
    @NamedQuery(name = "Workshop.find", query = "SELECT o FROM Workshop o"),
    @NamedQuery(name = "Workshop.count", query = "SELECT count(o) FROM Workshop o")})
public class Workshop extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    @NotBlank
    private String name;
    private String note;
    private LogInfo logInfo = new LogInfo();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LogInfo getLogInfo() {
        return logInfo;
    }

    public void setLogInfo(LogInfo logInfo) {
        this.logInfo = logInfo;
    }

    @Override
    public String toString() {
        return getName();
    }
}
