/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hengyi.japp.materiel.domain;

import static com.hengyi.japp.materiel.Constant.UPLOADFILE_ROOTPATH;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author jzb
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "T_UPLOADEDFILEWRAPPER")
@NamedQueries({
    @NamedQuery(name = "UploadedFileWrapper.find", query = "SELECT o FROM UploadedFileWrapper o"),
    @NamedQuery(name = "UploadedFileWrapper.count", query = "SELECT count(o) FROM UploadedFileWrapper o")})
public class UploadedFileWrapper extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    @NotBlank
    private String fileName;
    private String fileType;
    private long fileSize;
    @XmlTransient
    @ManyToMany(mappedBy = "photographs")
    private List<Mara> maras;
    private LogInfo logInfo = new LogInfo();

    public UploadedFileWrapper(UploadedFile uploadedFile) {
        fileName = uploadedFile.getFileName();
        fileType = uploadedFile.getContentType();
        fileSize = uploadedFile.getSize();
        uuid();
    }

    public String getSavedFullPath() {
        return UPLOADFILE_ROOTPATH + "/" + getSavedFileName();
    }

    /**
     * 保存在服务器上的文件名
     *
     * @return uuid+原始文件的后缀名
     */
    public String getSavedFileName() {
        return getId() + "." + FilenameUtils.getExtension(fileName);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public List<Mara> getMaras() {
        return maras;
    }

    public void setMaras(List<Mara> maras) {
        this.maras = maras;
    }

    public LogInfo getLogInfo() {
        return logInfo;
    }

    public void setLogInfo(LogInfo logInfo) {
        this.logInfo = logInfo;
    }

    @Override
    public String toString() {
        return getFileName();
    }

    public UploadedFileWrapper() {
        super();
    }
}
