package com.hengyi.japp.materiel.domain;

import static com.fasterxml.uuid.Generators.timeBasedGenerator;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@MappedSuperclass
public class AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @NotBlank
    @Id
    @Column(length = 36)
    protected String id;

    @PrePersist
    public void uuid() {
        if (id == null) {
            setId(timeBasedGenerator().generate().toString());
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isNew() {
        return getId() == null;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getId() == null || getClass() != object.getClass()) {
            return false;
        }
        AbstractEntity other = (AbstractEntity) object;
        return Objects.equal(getId(), other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).addValue(getId()).toString();
    }
}
