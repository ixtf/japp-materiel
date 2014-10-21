package com.hengyi.japp.materiel.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "T_SAPMARA")
@NamedQueries({
    @NamedQuery(name = "SapMara.find", query = "SELECT o FROM SapMara o"),
    @NamedQuery(name = "SapMara.count", query = "SELECT count(o) FROM SapMara o")})
/**
 * 物料主数据
 */
public class SapMara implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(length = 18)
    private String matnr;
    @Column(length = 3)
    private String meins;
    @OneToOne
    @PrimaryKeyJoinColumn
    private SapMakt sapMakt;
    /**
     * 物料组
     */
    @ManyToOne
    @JoinColumn(name = "MATKL")
    private SapT023t sapT023t;

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getMeins() {
        return meins;
    }

    public void setMeins(String meins) {
        this.meins = meins;
    }

    public SapMakt getSapMakt() {
        return sapMakt;
    }

    public void setSapMakt(SapMakt sapMakt) {
        this.sapMakt = sapMakt;
    }

    public SapT023t getSapT023t() {
        return sapT023t;
    }

    public void setSapT023t(SapT023t sapT023t) {
        this.sapT023t = sapT023t;
    }

    @Override
    public String toString() {
        return getSapMakt().toString();
    }
}
