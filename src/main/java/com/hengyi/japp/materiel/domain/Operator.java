package com.hengyi.japp.materiel.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "T_OPERATOR")
@NamedQueries({
    @NamedQuery(name = "Operator.find", query = "SELECT o FROM Operator o"),
    @NamedQuery(name = "Operator.count", query = "SELECT COUNT(o) FROM Operator o"),
    @NamedQuery(name = "Operator.queryByName", query = "SELECT o FROM Operator o WHERE o.name LIKE :name")})
public class Operator extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    @NotBlank
    @Column(length = 50)
    private String name;
    @XmlTransient
    @Column(length = 20)
    private String theme;

    public void setName(String name) {
        this.name = StringUtils.trim(name);
    }

    public Operator(String id, String name) {
        this();
        setId(id);
        setName(name);
    }

    public Operator() {
        super();
    }

    public String getName() {
        return name;
    }

    public String getTheme() {
        return theme == null ? "bootstrap" : theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return getName();
    }
}
