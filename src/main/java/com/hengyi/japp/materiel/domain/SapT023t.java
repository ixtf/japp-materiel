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
@Table(name = "T_SAPT023T")
@NamedQueries({
    @NamedQuery(name = "SapT023t.find", query = "SELECT o FROM SapT023t o"),
    @NamedQuery(name = "SapT023t.count", query = "SELECT count(o) FROM SapT023t o"),
    @NamedQuery(name = "SapT023t.queryByWgbez", query = "SELECT o FROM SapT023t o WHERE o.wgbez LIKE :wgbez")})
/**
 * 物料组描述
 */
public class SapT023t implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(length = 9)
    private String matkl;
    @Column(length = 40)
    private String wgbez;

    public String getMatkl() {
        return matkl;
    }

    public void setMatkl(String matkl) {
        this.matkl = matkl;
    }

    public String getWgbez() {
        return wgbez;
    }

    public void setWgbez(String wgbez) {
        this.wgbez = wgbez;
    }

    @Override
    public String toString() {
        return getWgbez();
    }
}
