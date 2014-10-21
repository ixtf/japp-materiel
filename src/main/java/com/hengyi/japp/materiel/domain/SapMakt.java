package com.hengyi.japp.materiel.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "T_SAPMAKT")
@NamedQueries({
    @NamedQuery(name = "SapMakt.find", query = "SELECT o FROM SapMakt o"),
    @NamedQuery(name = "SapMakt.count", query = "SELECT count(o) FROM SapMakt o")})
/**
 * 物料描述
 */
public class SapMakt implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(length = 18)
    private String matnr;
    @Column(length = 80)
    private String maktx;

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getMaktx() {
        return maktx;
    }

    public void setMaktx(String maktx) {
        this.maktx = maktx;
    }

    @Override
    public String toString() {
        return getMaktx();
    }
}
