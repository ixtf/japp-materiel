package com.hengyi.japp.materiel.service;

import static com.hengyi.japp.shiro.ShiroUtil.isAdmin;
import static com.hengyi.japp.shiro.ShiroUtil.setLoginUser;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.hengyi.japp.dto.LoginUserDTO;
import com.hengyi.japp.event.InitEvent;
import com.hengyi.japp.event.LoginEvent;
import com.hengyi.japp.event.LogoutEvent;
import com.hengyi.japp.event.ThemeUpdateEvent;
import com.hengyi.japp.materiel.event.ImportSapMaraEvent;
import com.hengyi.japp.materiel.event.ScanMaraPhotographsEvent;
import com.hengyi.japp.materiel.event.WorkshopUpdatedEvent;
import com.hengyi.japp.materiel.push.ImportSapMaraResource;

import javax.ejb.AccessTimeout;
import javax.faces.application.FacesMessage;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

@Startup
@Singleton
@Lock(LockType.READ)
@AccessTimeout(value = -1)
public class EventService {

    @Inject
    private Logger log;
    @EJB
    private CacheService cacheService;
    @EJB
    private SapFacade sapFacade;
    @EJB
    private OperatorService operatorService;

    @Asynchronous
    public void onEvent(@Observes ImportSapMaraEvent event) {
        long start = System.currentTimeMillis();
        if (event.getI_FIRST() != null) {
            sapFacade.importSapMaraByPage(event.getI_FIRST(), event.getI_PAGESIZE());
        } else {
            sapFacade.importSapMaraByMatnr(event.getMatnrs());
        }
        long end = System.currentTimeMillis();
        EventBus eventBus = EventBusFactory.getDefault().eventBus();
        FacesMessage message = new FacesMessage("SapMara导入成功", "用时：" + (end - start) / 1000 + "秒");
        eventBus.publish(ImportSapMaraResource.CHANNEL, message);
    }

    @Asynchronous
    public void onEvent(@Observes ScanMaraPhotographsEvent event) {
        operatorService.scanMaraPhotographs();
    }

    @Asynchronous
    public void onEvent(@Observes WorkshopUpdatedEvent event) {
        cacheService.initAllWorkshop();
    }

    public void onEvent(@Observes LoginEvent event) {
        if (isAdmin()) {
            return;
        }
        try {
            LoginUserDTO loginUser = sapFacade.findLoginUser(event.getUserType(), event.getBindId());
            setLoginUser(loginUser);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    @Asynchronous
    public void onEvent(@Observes ThemeUpdateEvent event) {
        cacheService.updateTheme(event.getTheme());
    }

    @Asynchronous
    public void onEvent(@Observes LogoutEvent event) {
        log.debug("UserType[" + event.getUserType() + "]，BindId[" + event.getBindId() + "]，已经退出！");
    }

    @Asynchronous
    public void onEvent(@Observes InitEvent event) {
        log.info("==============开始初始化================");
    }

}
