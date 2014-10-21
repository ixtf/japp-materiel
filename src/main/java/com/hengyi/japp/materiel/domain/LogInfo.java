package com.hengyi.japp.materiel.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Embeddable
public class LogInfo implements Serializable {

    @ManyToOne
    private Operator creator;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDateTime;
    @ManyToOne
    private Operator modifier;
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyDateTime;

    public void _operator(Operator operator) {
        if (getCreator() == null) {
            setCreator(operator);
            setCreateDateTime(new Date());
        }
        setModifier(operator);
        setModifyDateTime(new Date());
    }

    public Operator getCreator() {
        return creator;
    }

    public void setCreator(Operator creator) {
        this.creator = creator;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Operator getModifier() {
        return modifier;
    }

    public void setModifier(Operator modifier) {
        this.modifier = modifier;
    }

    public Date getModifyDateTime() {
        return modifyDateTime;
    }

    public void setModifyDateTime(Date modifyDateTime) {
        this.modifyDateTime = modifyDateTime;
    }
}
