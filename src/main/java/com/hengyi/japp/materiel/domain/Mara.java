/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hengyi.japp.materiel.domain;

import com.google.common.collect.Lists;
import com.hengyi.japp.data.PeriodTimeUnit;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jzb
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "T_MARA")
@NamedQueries({
    @NamedQuery(name = "Mara.find", query = "SELECT o FROM Mara o"),
    @NamedQuery(name = "Mara.count", query = "SELECT count(o) FROM Mara o")})
public class Mara implements Serializable {

    private static final long serialVersionUID = 1L;
    @OneToOne
    @PrimaryKeyJoinColumn
    private SapMara sapMara;
    @Id
    @Column(length = 18)
    private String matnr;
    private boolean maintenance;
    private long maintenanceOffSet;
    private PeriodTimeUnit maintenancePeriodTimeUnit = PeriodTimeUnit.DAYS;
    @ManyToOne
    private Workshop maintenanceWorkshop;
    private boolean oldReplaceNew;
    private BigDecimal lowestStock = BigDecimal.ZERO;
    private BigDecimal highestStock = BigDecimal.ZERO;
    private boolean importantAttachment;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "T_MARA_T_PHOTOGRAPH")
    private List<UploadedFileWrapper> photographs;
    private boolean haveDrawing;
    private long purchaseOffSet;
    private PeriodTimeUnit purchasePeriodTimeUnit = PeriodTimeUnit.DAYS;
    private LogInfo logInfo = new LogInfo();

    public Mara(SapMara sapMara) {
        this.sapMara = sapMara;
        setMatnr(sapMara.getMatnr());
    }

    public SapMara getSapMara() {
        return sapMara;
    }

    public void setSapMara(SapMara sapMara) {
        this.sapMara = sapMara;
    }

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public boolean isMaintenance() {
        return maintenance;
    }

    public void setMaintenance(boolean maintenance) {
        this.maintenance = maintenance;
    }

    public long getMaintenanceOffSet() {
        return maintenanceOffSet;
    }

    public void setMaintenanceOffSet(long maintenanceOffSet) {
        this.maintenanceOffSet = maintenanceOffSet;
    }

    public PeriodTimeUnit getMaintenancePeriodTimeUnit() {
        return maintenancePeriodTimeUnit;
    }

    public void setMaintenancePeriodTimeUnit(PeriodTimeUnit maintenancePeriodTimeUnit) {
        this.maintenancePeriodTimeUnit = maintenancePeriodTimeUnit;
    }

    public Workshop getMaintenanceWorkshop() {
        return maintenanceWorkshop;
    }

    public void setMaintenanceWorkshop(Workshop maintenanceWorkshop) {
        this.maintenanceWorkshop = maintenanceWorkshop;
    }

    public boolean isOldReplaceNew() {
        return oldReplaceNew;
    }

    public void setOldReplaceNew(boolean oldReplaceNew) {
        this.oldReplaceNew = oldReplaceNew;
    }

    public BigDecimal getLowestStock() {
        return lowestStock;
    }

    public void setLowestStock(BigDecimal lowestStock) {
        this.lowestStock = lowestStock;
    }

    public BigDecimal getHighestStock() {
        return highestStock;
    }

    public void setHighestStock(BigDecimal highestStock) {
        this.highestStock = highestStock;
    }

    public boolean isImportantAttachment() {
        return importantAttachment;
    }

    public void setImportantAttachment(boolean importantAttachment) {
        this.importantAttachment = importantAttachment;
    }

    public List<UploadedFileWrapper> getPhotographs() {
        return photographs;
    }

    public void setPhotographs(List<UploadedFileWrapper> photographs) {
        this.photographs = photographs;
    }

    public boolean isHaveDrawing() {
        return haveDrawing;
    }

    public void setHaveDrawing(boolean haveDrawing) {
        this.haveDrawing = haveDrawing;
    }

    public long getPurchaseOffSet() {
        return purchaseOffSet;
    }

    public void setPurchaseOffSet(long purchaseOffSet) {
        this.purchaseOffSet = purchaseOffSet;
    }

    public PeriodTimeUnit getPurchasePeriodTimeUnit() {
        return purchasePeriodTimeUnit;
    }

    public void setPurchasePeriodTimeUnit(PeriodTimeUnit purchasePeriodTimeUnit) {
        this.purchasePeriodTimeUnit = purchasePeriodTimeUnit;
    }

    public LogInfo getLogInfo() {
        return logInfo;
    }

    public void setLogInfo(LogInfo logInfo) {
        this.logInfo = logInfo;
    }

    @Override
    public String toString() {
        return sapMara == null ? null : sapMara.toString();
    }

    public Mara() {
        super();
    }

    public void add(UploadedFileWrapper photograph) {
        if (photographs == null) {
            photographs = Lists.newArrayList(photograph);
        } else {
            photographs.add(photograph);
        }
    }

    public void remove(UploadedFileWrapper photograph) {
        photographs.remove(photograph);
    }

}
