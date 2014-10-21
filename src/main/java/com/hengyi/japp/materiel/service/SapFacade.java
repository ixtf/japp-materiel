package com.hengyi.japp.materiel.service;

import static com.hengyi.japp.materiel.Constant.SapFunction.ZIMPORTSAPMARA_BYMATNR;
import static com.hengyi.japp.sap.SapUtil.convert;

import java.util.Map;

import javax.ejb.AccessTimeout;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.dozer.Mapper;

import com.google.common.collect.Maps;
import static com.hengyi.japp.materiel.Constant.SapFunction.ZIMPORTSAPMARA_BYPAGE;
import com.hengyi.japp.materiel.domain.SapMakt;
import com.hengyi.japp.materiel.domain.SapMara;
import com.hengyi.japp.materiel.domain.SapT023t;
import com.hengyi.japp.sap.BaseSapService;
import com.hengyi.japp.sap.DestinationType;
import com.hengyi.japp.sap.dto.SapMaktDTO;
import com.hengyi.japp.sap.dto.SapMaraDTO;
import com.hengyi.japp.sap.dto.SapT023tDTO;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;

@Startup
@Singleton
@Lock(LockType.READ)
@AccessTimeout(value = -1)
public class SapFacade extends BaseSapService {

    @Resource
    private ManagedScheduledExecutorService executor;
    @Inject
    private Mapper dozer;
    @EJB
    private OperatorService operatorService;

    @Override
    protected DestinationType getDestinationType() {
        return DestinationType.PRO;
    }

    public void importSapMaraByPage(Integer I_FIRST, Integer I_PAGESIZE) {
        try {
            JCoDestination dest = getDestination();
            JCoFunction f = getFunction(ZIMPORTSAPMARA_BYPAGE, dest);
            f.getImportParameterList().setValue("I_FIRST", I_FIRST);
            f.getImportParameterList().setValue("I_PAGESIZE", I_PAGESIZE);
            execute(f, dest);
            saveSapMara(f);
        } catch (Exception ex) {
            Logger.getLogger(SapFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param matnrs 需要导入的sap物料号，null为导入全部
     */
    public void importSapMaraByMatnr(Iterable<String> matnrs) {
        try {
            JCoDestination dest = getDestination();
            JCoFunction f = getFunction(ZIMPORTSAPMARA_BYMATNR, dest);
            JCoTable t = f.getTableParameterList().getTable("IT_MARA");
            if (matnrs != null) {
                t.clear();
                for (String matnr : matnrs) {
                    t.appendRow();
                    t.setValue("MATNR", matnr);
                }
            }
            execute(f, dest);
            saveSapMara(f);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void saveSapMara(JCoFunction f) throws Exception {
        Map<String, SapMakt> sapMaktMap = Maps.newHashMap();
        for (SapMaktDTO dto : convert(f.getTableParameterList().getTable("ET_MAKT"), SapMaktDTO.class)) {
            SapMakt sapMakt = dozer.map(dto, SapMakt.class);
            sapMaktMap.put(sapMakt.getMatnr(), sapMakt);
        }

        Map<String, SapT023t> sapT023tMap = Maps.newHashMap();
        for (SapT023tDTO dto : convert(f.getTableParameterList().getTable("ET_T023T"), SapT023tDTO.class)) {
            SapT023t sapT023t = dozer.map(dto, SapT023t.class);
            sapT023tMap.put(sapT023t.getMatkl(), sapT023t);
        }

        for (SapMaraDTO dto : convert(f.getTableParameterList().getTable("ET_MARA"), SapMaraDTO.class)) {
            SapMara sapMara = dozer.map(dto, SapMara.class);
            sapMara.setSapMakt(sapMaktMap.get(sapMara.getMatnr()));
            sapMara.setSapT023t(sapT023tMap.get(dto.getMatkl()));
            executor.submit(new SaveSapMaraJob(sapMara));
        }
    }

    class SaveSapMaraJob implements Runnable {

        private final SapMara sapMara;

        public SaveSapMaraJob(SapMara sapMara) {
            this.sapMara = sapMara;
        }

        @Override
        public void run() {
            operatorService.save(sapMara);
        }
    }
}
