package com.hengyi.japp.materiel.service;

import static com.google.common.base.Predicates.in;
import static com.google.common.base.Predicates.not;
import static com.google.common.collect.Collections2.filter;
import com.google.common.collect.ImmutableList;
import com.hengyi.japp.materiel.domain.Mara;
import com.hengyi.japp.materiel.domain.SapMara;
import com.hengyi.japp.materiel.domain.UploadedFileWrapper;
import com.hengyi.japp.materiel.domain.Workshop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.io.FileUtils;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;

@Stateless
public class OperatorService {

    @Inject
    private Logger log;
    @PersistenceContext
    private EntityManager em;

    public void save(SapMara sapMara) {
        em.merge(sapMara);
    }

    public Mara save(Mara mara, List<UploadedFileWrapper> photographs, Map<UploadedFileWrapper, UploadedFile> uploadedMap) throws Exception {
        //当前图片为null，表示老的图片全部删除
        if (photographs == null) {
            photographs = ImmutableList.of();
        }
        if (uploadedMap != null) {
            for (Map.Entry<UploadedFileWrapper, UploadedFile> entry : uploadedMap.entrySet()) {
                entry.getValue().write(entry.getKey().getSavedFullPath());
            }
        }
        deleteOldPhotographs(mara.getMatnr(), photographs);
        for (UploadedFileWrapper photograph : photographs) {
            em.merge(photograph);
        }
        mara.setPhotographs(photographs);
        return em.merge(mara);
    }

    private void deleteOldPhotographs(String matnr, List<UploadedFileWrapper> photographs) {
        Mara oldMara = em.find(Mara.class, matnr);
        if (oldMara == null) {
            return;
        }
        List<UploadedFileWrapper> oldPhotographs = oldMara.getPhotographs();
        if (oldPhotographs == null) {
            return;
        }
        for (UploadedFileWrapper delete : filter(oldPhotographs, not(in(photographs)))) {
            File file = new File(delete.getSavedFullPath());
            if (file.exists()) {
                try {
                    FileUtils.forceDelete(file);
                } catch (IOException e) {
                    log.error("文件【" + delete.getSavedFullPath() + "】删除失败！", e);
                }
            }
            em.remove(delete);
        }
    }

//    public void addPhotograph(Mara mara, UploadedFile uploadedFile) throws IOException {
//        UploadedFileWrapper fileWrapper = new UploadedFileWrapper(uploadedFile);
//        fileWrapper.getLogInfo()._operator(mara.getLogInfo().getCreator());
//        Util.createFile(fileWrapper.getSavedName(), uploadedFile);
//        em.merge(fileWrapper);
//        mara.add(fileWrapper);
//        em.merge(mara);
//    }
//    public void removePhotograph(Mara mara, UploadedFileWrapper fileWrapper) throws IOException {
//        fileWrapper = em.find(UploadedFileWrapper.class, fileWrapper.getId());
//        Util.deleteFile(fileWrapper);
//        em.remove(fileWrapper);
//        mara.remove(fileWrapper);
//        em.merge(mara);
//    }
    public void save(Workshop workshop) {
        if (workshop.isNew()) {
            workshop.uuid();
        }
        em.merge(workshop);
    }

    public void scanMaraPhotographs() {
        //TODO 图片文件扫描
    }
}
