package com.hengyi.japp.materiel;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.google.common.collect.Lists;
import static com.hengyi.japp.materiel.Constant.UPLOADFILE_ROOTPATH;
import com.hengyi.japp.materiel.domain.Mara;
import com.hengyi.japp.materiel.domain.Mara_;
import com.hengyi.japp.materiel.domain.SapMara;
import com.hengyi.japp.materiel.domain.Operator;
import com.hengyi.japp.materiel.domain.Operator_;
import com.hengyi.japp.materiel.domain.SapMakt_;
import com.hengyi.japp.materiel.domain.SapMara_;
import com.hengyi.japp.materiel.domain.UploadedFileWrapper;
import com.hengyi.japp.materiel.domain.Workshop;
import com.hengyi.japp.materiel.domain.Workshop_;
import com.hengyi.japp.materiel.web.command.MaraQueryCommand;
import com.hengyi.japp.materiel.web.command.OperatorQueryCommand;
import com.hengyi.japp.materiel.web.command.SapMaraQueryCommand;
import com.hengyi.japp.materiel.web.command.WorkshopQueryCommand;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.primefaces.model.UploadedFile;

public class Util {

    public static void queryCommand(CriteriaBuilder cb, CriteriaQuery<?> cq, Root<Operator> root, OperatorQueryCommand command) {
        if (!isBlank(command.getName())) {
            cq.where(cb.like(root.get(Operator_.name), command.getNameQuery()));
        }
    }

    public static void queryCommand(CriteriaBuilder cb, CriteriaQuery<?> cq, Root<Workshop> root, WorkshopQueryCommand command) {
        if (!isBlank(command.getName())) {
            cq.where(cb.like(root.get(Workshop_.name), command.getNameQuery()));
        }
    }

    public static void queryCommand(CriteriaBuilder cb, CriteriaQuery<?> cq, Root<SapMara> root, SapMaraQueryCommand command) {
        List<Predicate> ps = Lists.newArrayList();
        if (!isBlank(command.getMaktx())) {
            ps.add(cb.like(root.get(SapMara_.sapMakt).get(SapMakt_.maktx), command.getMaktxQuery()));
        }
        if (command.getSapT023t() != null) {
            ps.add(cb.equal(root.get(SapMara_.sapT023t), command.getSapT023t()));
        }
        cq.where(ps.toArray(new Predicate[ps.size()]));
    }

    public static void queryCommand(CriteriaBuilder cb, CriteriaQuery<?> cq, Root<Mara> root, MaraQueryCommand command) {
        List<Predicate> ps = Lists.newArrayList();
        if (!isBlank(command.getMaktx())) {
            ps.add(cb.like(root.get(Mara_.sapMara).get(SapMara_.sapMakt).get(SapMakt_.maktx), command.getMaktxQuery()));
        }
        if (command.getSapT023t() != null) {
            ps.add(cb.equal(root.get(Mara_.sapMara).get(SapMara_.sapT023t), command.getSapT023t()));
        }
        cq.where(ps.toArray(new Predicate[ps.size()]));
    }

    public static void createFile(String fileName, UploadedFile uploadedFile) throws IOException {
        File dir = new File(UPLOADFILE_ROOTPATH);
        if (!dir.exists()) {
            FileUtils.forceMkdir(dir);
        }
        FileUtils.writeByteArrayToFile(new File(dir, fileName), uploadedFile.getContents());
    }

    public static void deleteFile(UploadedFileWrapper fileWrapper) throws IOException {
        File file = new File(UPLOADFILE_ROOTPATH, fileWrapper.getSavedFileName());
        if (file.exists()) {
            FileUtils.forceDelete(file);
        }
    }
}
