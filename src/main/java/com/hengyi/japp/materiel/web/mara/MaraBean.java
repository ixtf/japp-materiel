package com.hengyi.japp.materiel.web.mara;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hengyi.japp.materiel.domain.Mara;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.Parameter;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.faces.annotation.Deferred;

import com.hengyi.japp.materiel.domain.SapMara;
import com.hengyi.japp.materiel.domain.UploadedFileWrapper;
import com.hengyi.japp.materiel.service.CacheService;
import com.hengyi.japp.materiel.service.OperatorService;
import com.hengyi.japp.materiel.service.QueryService;
import com.hengyi.japp.web.AbstractBean;
import com.hengyi.japp.web.RewriteUrl;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named
@ViewScoped
@Join(path = "/mara", to = "/faces/mara/update.jsf")
public class MaraBean extends AbstractBean {

    private static final long serialVersionUID = 1L;
    @EJB
    private CacheService cacheService;
    @EJB
    private QueryService queryService;
    @EJB
    private OperatorService operatorService;
    @Deferred
    @Parameter
    private String matnr;
    private Mara mara;
    private List<UploadedFileWrapper> photographs;
    private UploadedFileWrapper photograph;
    private Map<UploadedFileWrapper, UploadedFile> uploadedMap;

    @Deferred
    @RequestAction
    public void requestAction() throws IOException {
        if (mara != null) {
            return;
        }
        try {
            mara = queryService.find(Mara.class, matnr);
            if (mara == null) {
                mara = new Mara(queryService.find(SapMara.class, matnr));
            }
            photographs = mara.getPhotographs();
        } catch (Exception e) {
            RewriteUrl.to(MarasBean.class).redirect();
        }
    }

    synchronized public void addPhotograph(FileUploadEvent event) {
        try {
            UploadedFileWrapper _photograph = new UploadedFileWrapper(event.getFile());
            _photograph.getLogInfo()._operator(null);
            mara.getLogInfo()._operator(cacheService.getCurrentOperator());
            if (uploadedMap == null) {
                uploadedMap = Maps.newHashMap();
            }
            uploadedMap.put(_photograph, event.getFile());
            if (photographs == null) {
                photographs = Lists.newArrayList();
            }
            photographs.add(_photograph);
        } catch (Exception e) {
            errorMessage(e);
        }
    }

    public void removePhotograph() {
        try {
            photographs.remove(photograph);
            if (uploadedMap != null) {
                uploadedMap.remove(photograph);
            }
        } catch (Exception e) {
            errorMessage(e);
        }
    }

    public void save() {
        try {
            mara.getLogInfo()._operator(cacheService.getCurrentOperator());
            operatorService.save(mara, photographs, uploadedMap);
            operationSuccessMessage();
        } catch (Exception e) {
            errorMessage(e);
        }
    }

//    public void addPhotograph(FileUploadEvent event) {
//        try {
//            mara.getLogInfo()._operator(cacheService.getCurrentOperator());
//            operatorService.addPhotograph(mara, event.getFile());
//            RewriteUrl.to(MaraBean.class).with("matnr", mara.getMatnr()).redirect();
//        } catch (Exception e) {
//            errorMessage(e);
//        }
//    }
//    public void removePhotograph() {
//        try {
//            mara.getLogInfo()._operator(cacheService.getCurrentOperator());
//            operatorService.removePhotograph(mara, photograph);
//            RewriteUrl.to(MaraBean.class).with("matnr", mara.getMatnr()).redirect();
//        } catch (Exception e) {
//            errorMessage(e);
//        }
//    }
    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public Mara getMara() {
        return mara;
    }

    public void setMara(Mara mara) {
        this.mara = mara;
    }

    public List<UploadedFileWrapper> getPhotographs() {
        return photographs;
    }

    public void setPhotographs(List<UploadedFileWrapper> photographs) {
        this.photographs = photographs;
    }

    public UploadedFileWrapper getPhotograph() {
        return photograph;
    }

    public void setPhotograph(UploadedFileWrapper photograph) {
        this.photograph = photograph;
    }
}
